/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 *
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

package algopad.common.ds;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * Custom {@link Map} implementation where the keys are strings,
 * permitting various convenience methods for querying on key prefixes.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("CharUsedInArithmeticContext")
public class PrefixMap<V> extends AbstractMap<CharSequence, V> {

    private final char first;
    private final int  radix;

    private final Node root;
    private       int  size;

    public PrefixMap(final CharRange range) {
        this.first = range.getFrom();
        this.radix = range.size();
        this.root = new Node(radix);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @Nullable
    public V get(final Object key) {
        final Node node = nodeForKey(key);
        return node != null ? node.getValue() : null;
    }

    @Override
    public V put(final CharSequence key, final V value) {

        requireNonNull(key);
        requireNonNull(value);

        Node node = root;
        int i = 0;
        while (i < key.length()) {
            final int idx = key.charAt(i) - first;
            if (node.nodes[idx] == null) {
                node.nodes[idx] = new Node(radix);
            }
            node = node.nodes[idx];
            i++;
        }

        final V prev = node.getValue();
        if (prev == null) { size++; }
        node.value = value;
        return prev;
    }

    @Override
    public V remove(final Object key) {
        final Node node = nodeForKey(key);
        return removeNode(node);
    }

    @Override
    public void clear() {
        final Node[] nodes = root.nodes;
        for (int i = 0; i < radix; i++) {
            //noinspection AssignmentToNull (clear to let GC do its work)
            nodes[i] = null;
        }
        size = 0;
    }

    @Override
    @Nonnull
    public Set<Entry<CharSequence, V>> entrySet() {
        //noinspection ReturnOfInnerClass
        return new PrefixEntrySet();
    }

    @Nullable
    private Node nodeForKey(final Object key) {
        if (!(key instanceof CharSequence)) {
            return null;
        }
        final CharSequence cs = (CharSequence) key;
        Node node = root;
        int i = 0;
        while (i < cs.length() && node != null) {
            final int idx = cs.charAt(i) - first;
            node = node.nodes[idx];
            i++;
        }
        return node;
    }

    @Nullable
    @SuppressWarnings("NonBooleanMethodNameMayNotStartWithQuestion")
    private V removeNode(final Node node) {
        V prev = null;
        if (node != null && node.value != null) {
            prev = node.getValue();
            node.value = null;
            size--;
        }
        return prev;
    }

    /**
     * @return all {@link Entry}s of this prefix map whose keys' begin with <i>prefix</i>.
     */
    public Iterable<Entry<CharSequence, V>> entriesWithPrefix(final String prefix) {
        final Queue<Entry<CharSequence, V>> entries = new LinkedQueue<>();
        collectEntries(nodeForKey(prefix), prefix, entries);
        return entries;
    }

    private void collectEntries(final Node node, final String key,
                                final Queue<Entry<CharSequence, V>> entries) {
        if (node == null) { return; }
        final V value = node.getValue();
        if (value != null) {
            entries.offer(new SimpleImmutableEntry<>(key, value));
        }
        for (int i = 0; i < radix; i++) {
            final Node child = node.nodes[i];
            if (child != null) {
                //noinspection NumericCastThatLosesPrecision
                final char c = (char) (i + first);
                collectEntries(child, key + c, entries);
            }
        }
    }

    /**
     * Trie node.
     */
    private static final class Node {

        private final Node[] nodes;
        private       Object value;

        private Node(final int len) {
            this.nodes = new Node[len];
        }

        private <V> V getValue() {
            //noinspection unchecked
            return (V) value;
        }
    }

    @SuppressWarnings("NonStaticInnerClassInSecureContext")
    private class PrefixEntrySet extends AbstractSet<Entry<CharSequence, V>> {

        @Override
        @Nonnull
        public Iterator<Entry<CharSequence, V>> iterator() {
            return entriesWithPrefix("").iterator();
        }

        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry<?, ?> entry = (Entry<?, ?>) o;
            return nodeForEntry(entry) != null;
        }

        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry<?, ?> entry = (Entry<?, ?>) o;
            final Node node = nodeForEntry(entry);
            return removeNode(node) != null;
        }

        @Nullable
        private Node nodeForEntry(final Entry<?, ?> entry) {
            final Node node = nodeForKey(entry.getKey());
            final boolean match = node != null && Objects.equals(entry.getValue(), node.value);
            return match ? node : null;
        }

        @Override
        public int size() {
            return PrefixMap.this.size();
        }

        @Override
        public void clear() {
            PrefixMap.this.clear();
        }
    }
}
