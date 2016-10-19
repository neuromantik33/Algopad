/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 * Licensed under the MIT License, the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package algopad.algorithms.pt2.w3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * This class represents a baseball division and determines which teams are
 * mathematically eliminated  by reducing it to the maxflow problem.
 * To check whether team x is eliminated, we consider two cases :
 * <ul>
 * <li><i>Trivial elimination</i>. If the maximum number of games team <i>x</i>
 * can win is less than the number of wins of some other team <i>i</i>, then
 * team <i>x</i> is trivially eliminated. That is, if <i>w[x] + r[x] < w[i]</i>,
 * then team <i>x</i> is mathematically eliminated.</li>
 * <li><i>Nontrivial elimination</i>. Otherwise, we create a flow network and
 * solve a maxflow problem in it. In the network, feasible integral flows
 * correspond to outcomes of the remaining schedule. There are vertices
 * corresponding to teams (other than team <i>x</i>) and to remaining divisional
 * games (not involving team <i>x</i>). Intuitively, each unit of flow in the
 * network corresponds to a remaining game. As it flows through the network from
 * <i>s</i> to <i>t</i>, it passes from a game vertex, say between teams <i>i</i>
 * and <i>j</i>, then through one of the team vertices <i>i</i> or <i>j</i>,
 * classifying this game as being won by that team.</li>
 * </ul>
 */

@SuppressWarnings("MethodWithMultipleLoops")
public class BaseballElimination {

    private Map<String, Integer> teamIds;
    private int[]                wins;
    private int[]                losses;
    private int[]                left;
    private int[][]              games;

    private final Map<String, EliminationInfo> eliminationInfos;

    public BaseballElimination(final String filename) {
        this.eliminationInfos = new HashMap<>();
        final In input = new In(filename);
        try {
            final int size = input.readInt();
            initStatistics(size);
            parseFile(size, input);
        } finally {
            input.close();
        }
    }

    private void initStatistics(final int size) {
        this.teamIds = new LinkedHashMap<>(size);
        this.wins = new int[size];
        this.losses = new int[size];
        this.left = new int[size];
        this.games = new int[size][size];
    }

    private void parseFile(final int size, final In input) {
        for (int i = 0; i < size; i++) {
            final String team = input.readString();
            teamIds.put(team, i);
            wins[i] = input.readInt();
            losses[i] = input.readInt();
            left[i] = input.readInt();
            for (int j = 0; j < size; j++) {
                games[i][j] = input.readInt();
            }
        }
    }

    /**
     * @return the number of teams.
     */
    public int numberOfTeams() {
        return teamIds.size();
    }

    /**
     * @return the teams.
     */
    public Iterable<String> teams() {
        return teamIds.keySet();
    }

    /**
     * @return the number of wins for a given <i>team</i>.
     */
    public int wins(final String team) {
        return wins[teamId(team)];
    }

    /**
     * @return the number of losses for a given <i>team</i>.
     */
    public int losses(final String team) {
        return losses[teamId(team)];
    }

    /**
     * @return the number of remaining games for a given <i>team</i>.
     */
    public int remaining(final String team) {
        return left[teamId(team)];
    }

    /**
     * @return the number of remaining games between <i>team1</i> and <i>team2</i>.
     */
    public int against(final String team1, final String team2) {
        return games[teamId(team1)][teamId(team2)];
    }

    /**
     * @return {@code true} if the <i>team</i> is eliminated,
     * {@code false} otherwise.
     */
    public boolean isEliminated(final String team) {
        return getOrCreateEliminationFor(team).eliminated;
    }

    /**
     * @return the subset R of teams that eliminates the given <i>team</i>,
     * {@code null} if not eliminated.
     */
    public Iterable<String> certificateOfElimination(final String team) {
        return getOrCreateEliminationFor(team).certificate;
    }

    private EliminationInfo getOrCreateEliminationFor(final String team) {
        EliminationInfo info = eliminationInfos.get(team);
        if (info == null) {
            info = eliminationFor(team);
            eliminationInfos.put(team, info);
        }
        return info;
    }

    private EliminationInfo eliminationFor(final String team) {
        EliminationInfo info = trivialEliminationFor(team);
        if (info == null) {
            info = nonTrivialEliminationFor(team);
        }
        return info;
    }

    private EliminationInfo trivialEliminationFor(final String team) {
        EliminationInfo info = null;
        final int possibleWins = wins(team) + remaining(team);
        for (final String other : teams()) {
            if (possibleWins < wins(other)) {
                info = new EliminationInfo();
                info.eliminated = true;
                info.certificate = Collections.singletonList(other);
            }
        }
        return info;
    }

    private EliminationInfo nonTrivialEliminationFor(final String teamName) {
        final int team = teamId(teamName);
        return new NetworkBuilder(team).calculateElimination();
    }

    private int teamId(final String team) {
        if (!teamIds.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return teamIds.get(team);
    }

    @SuppressWarnings({ "MethodCanBeVariableArityMethod", "StringConcatenation" })
    public static void main(final String[] args) {
        final BaseballElimination division = new BaseballElimination(args[0]);
        for (final String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (final String t : division.certificateOfElimination(team)) {
                    //noinspection MagicCharacter
                    StdOut.print(t + ' ');
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

    private static class EliminationInfo {

        private boolean            eliminated;
        private Collection<String> certificate;

    }

    @SuppressWarnings({ "NonStaticInnerClassInSecureContext",
                        "NonBooleanMethodNameMayNotStartWithQuestion",
                        "UnnecessaryLocalVariable" })
    private final class NetworkBuilder {

        private final int         team;
        private final FlowNetwork network;

        private NetworkBuilder(final int team) {
            this.team = team;
            this.network = new FlowNetwork(calculateVertices());
        }

        private EliminationInfo calculateElimination() {

            final int numTeams = numberOfTeams();
            final int src = numTeams;
            //noinspection TooBroadScope
            final int sink = src + 1;

            addTeamToSinkEdges();

            final int requiredFlow = populateNetwork();
            final EliminationInfo info = new EliminationInfo();
            final FordFulkerson fdFlkrsn = new FordFulkerson(network, src, sink);

            // If the flow is less than the required flow,
            // then the team is eliminated
            if (fdFlkrsn.value() < requiredFlow) {
                info.eliminated = true;
            }

            // If eliminated, calculate the certificate of elimination
            if (info.eliminated) {
                info.certificate = new ArrayList<>(numTeams);
                for (final String certTeam : teams()) {
                    if (fdFlkrsn.inCut(teamId(certTeam))) {
                        info.certificate.add(certTeam);
                    }
                }
            }

            return info;

        }

        private int populateNetwork() {

            final int numTeams = numberOfTeams();
            final int src = numTeams;

            int requiredFlow = 0;
            int game = src + 2;
            for (int i = 0; i < numTeams; i++) {
                for (int j = 1; j < numTeams; j++) {
                    if (i != team && j != team && i < j) {
                        final int numGames = games[i][j];
                        requiredFlow += numGames;
                        network.addEdge(new FlowEdge(src, game, numGames));
                        addGameToTeamEdge(game, i);
                        addGameToTeamEdge(game, j);
                        game++;
                    }
                }
            }

            return requiredFlow;

        }

        private int calculateVertices() {
            return /* Source and sink vertices */ 2 +
                   /* Game vertices */ binomialCoefficient(numberOfTeams() - 1) +
                   /* Team vertices */ numberOfTeams();
        }

        /**
         * Calculates the binomial coefficient.
         *
         * @see <a href="http://mathworld.wolfram.com/BinomialCoefficient.html">
         * BinomialCoefficient</a>
         */
        private int binomialCoefficient(final int n) {
            return n * (n - 1) / 2;
        }

        private void addTeamToSinkEdges() {
            final int numTeams = numberOfTeams();
            final int sink = numTeams + 1;
            for (int i = 0; i < numTeams; i++) {
                if (i != team) {
                    // We prevent team i from winning more than that many games
                    // in total, by including an edge from team vertex i to the
                    // sink vertex with capacity w[x] + r[x] - w[i]
                    final int capacity = wins[team] + left[team] - wins[i];
                    final FlowEdge edge = new FlowEdge(i, sink, capacity);
                    network.addEdge(edge);
                }
            }
        }

        private void addGameToTeamEdge(final int game, final int otherTeam) {
            network.addEdge(new FlowEdge(game, otherTeam, Double.POSITIVE_INFINITY));
        }
    }
}
