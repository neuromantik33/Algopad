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

package algopad.algorithms.pt2.w3

import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings('GroovyAccessibility')
class BaseballEliminationSpec extends Specification {

    BaseballElimination parseBaseballElimination(name) {
        def file = getClass().getResource(name).file
        new BaseballElimination(file)
    }

    @Unroll
    def "it should successfully extract #teams from the file '#input'"() {

        given:
        def be = parseBaseballElimination("${input}.txt")

        expect:
        be.numberOfTeams() == teams.size()
        be.teams() as List == teams

        where:
        input    | teams
        'teams1' | ['Turing']
        'teams4' | ['Atlanta', 'Philadelphia', 'New_York', 'Montreal']
        'teams5' | ['New_York', 'Baltimore', 'Boston', 'Toronto', 'Detroit']

    }

    @Unroll
    def "it should gather the statistics from the file '#input'"() {

        given:
        def be = parseBaseballElimination("${input}.txt")

        expect:
        be.wins(team) == wins
        be.losses(team) == losses
        be.remaining(team) == left

        where:
        input    | team           | wins | losses | left
        'teams4' | 'Atlanta'      | 83   | 71     | 8
        'teams4' | 'Philadelphia' | 80   | 79     | 3
        'teams4' | 'New_York'     | 78   | 78     | 6
        'teams4' | 'Montreal'     | 77   | 82     | 3
        'teams5' | 'New_York'     | 75   | 59     | 28
        'teams5' | 'Baltimore'    | 71   | 63     | 28
        'teams5' | 'Boston'       | 69   | 66     | 27
        'teams5' | 'Toronto'      | 63   | 72     | 27
        'teams5' | 'Detroit'      | 49   | 86     | 27

    }

    @Unroll
    def "it should calculate #games remaining games for #team against #opponent"() {

        given:
        def be = parseBaseballElimination("${input}.txt")

        expect:
        be.against(team, opponent) == games
        be.against(opponent, team) == games

        where:
        input    | team       | opponent       | games
        'teams4' | 'Atlanta'  | 'Philadelphia' | 1
        'teams4' | 'Atlanta'  | 'New_York'     | 6
        'teams4' | 'Atlanta'  | 'Montreal'     | 1
        'teams5' | 'New_York' | 'Baltimore'    | 3
        'teams5' | 'New_York' | 'Boston'       | 8
        'teams5' | 'New_York' | 'Toronto'      | 7
        'teams5' | 'New_York' | 'Detroit'      | 3

    }

    @Unroll
    def "it should trivially eliminate #team given '#input'"() {

        given:
        def be = parseBaseballElimination("${input}.txt")

        expect:
        be.isEliminated team
        be.certificateOfElimination(team) == certificate

        where:
        input     | team         | certificate
        'teams4'  | 'Montreal'   | ['Atlanta']
        'teams4a' | 'Bin_Ladin'  | ['Obama']
        'teams4b' | 'Hufflepuff' | ['Gryffindor']
        'teams4b' | 'Ravenclaw'  | ['Gryffindor']
        'teams4b' | 'Slytherin'  | ['Gryffindor']

    }

    @Unroll
    def "it should non-trivially eliminate #team given '#input"() {

        given:
        def be = parseBaseballElimination("${input}.txt")

        expect:
        be.isEliminated team
        be.certificateOfElimination(team) == certificate

        where:
        input     | team           | certificate
        'teams4'  | 'Philadelphia' | ['Atlanta', 'New_York']
        'teams4a' | 'Ghaddafi'     | ['CIA', 'Obama']
        'teams5'  | 'Detroit'      | ['New_York', 'Baltimore', 'Boston', 'Toronto']
        'teams7'  | 'Ireland'      | ['U.S.A.', 'France', 'Germany']
        'teams24' | 'Team13'       | ['Team14', 'Team15', 'Team17', 'Team20', 'Team22']
        'teams32' | 'Team25'       | ['Team0', 'Team6', 'Team8', 'Team11', 'Team26']
        'teams32' | 'Team29'       | ['Team0', 'Team6', 'Team8', 'Team11', 'Team26']
        'teams32' | 'Team25'       | ['Team0', 'Team6', 'Team8', 'Team11', 'Team26']
        'teams32' | 'Team29'       | ['Team0', 'Team6', 'Team8', 'Team11', 'Team26']
        'teams36' | 'Team21'       | ['Team18', 'Team20', 'Team22', 'Team23']
        'teams42' | 'Team6'        | ['Team0', 'Team18', 'Team19', 'Team21']
        'teams42' | 'Team15'       | ['Team0', 'Team18', 'Team19', 'Team21']
        'teams42' | 'Team25'       | ['Team0', 'Team18', 'Team19', 'Team21']
        'teams48' | 'Team6'        | ['Team38', 'Team39', 'Team40']
        //        'teams48' | 'Team23'       | []
        //        'teams48' | 'Team47'       | []
        'teams54' | 'Team3'        | ['Team33', 'Team34', 'Team36', 'Team41']
        'teams54' | 'Team29'       | ['Team33', 'Team34', 'Team36', 'Team41']
        'teams54' | 'Team37'       | ['Team33', 'Team34', 'Team36', 'Team41']
        'teams54' | 'Team50'       | ['Team33', 'Team34', 'Team36', 'Team41']

    }

    @Unroll
    def "it should not be able to determine if #team is eliminated from '#input'"() {

        given:
        def be = parseBaseballElimination("${input}.txt")

        expect:
        !be.isEliminated(team)
        be.certificateOfElimination(team) == null

        where:
        input    | team
        'teams4' | 'Atlanta'
        'teams4' | 'New_York'

    }
}
