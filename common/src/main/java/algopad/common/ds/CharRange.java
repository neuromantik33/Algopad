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

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

import org.codehaus.groovy.runtime.IteratorClosureAdapter;

import groovy.lang.Closure;
import groovy.lang.EmptyRange;
import groovy.lang.Range;

import static java.lang.String.format;

/**
 * Represents an inclusive list of {@link Character}s.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings({ "CharacterComparison", "CharUsedInArithmeticContext", "NumericCastThatLosesPrecision" })
public class CharRange extends AbstractList<Character> implements Range<Character>, RandomAccess {

    private final char    from;
    private final char    to;
    private final boolean reverse;

    // AsType constructor
    @SuppressWarnings("unused")
    private CharRange(final String[] strs) {
        this(strs[0].charAt(0), strs[strs.length - 1].charAt(0));
    }

    public CharRange(final char from, final char to) {
        this.reverse = from >= to;
        if (reverse) {
            this.from = to;
            this.to = from;
        } else {
            this.from = from;
            this.to = to;
        }
    }

    private CharRange(final char from, final char to, final boolean reverse) {
        if (from > to) {
            throw new IllegalArgumentException("'from' must be less than or equal to 'to'");
        }
        this.from = from;
        this.to = to;
        this.reverse = reverse;
    }

    @Override
    public Character getFrom() {
        return from;
    }

    @Override
    public Character getTo() {
        return to;
    }

    @Override
    public boolean isReverse() {
        return reverse;
    }

    @Override
    public boolean containsWithinBounds(final Object o) {
        return contains(o);
    }

    @Override
    @SuppressWarnings({ "AssignmentToMethodParameter",
                        "MethodWithMultipleLoops",
                        "rawtypes" })
    public void step(int step, final Closure closure) {
        if (step == 0) {
            if (from != to) {
                //noinspection ProhibitedExceptionThrown
                throw new RuntimeException("Infinite loop detected due to step size of 0");
            }
        }
        if (reverse) {
            step = -step;
        }
        if (step > 0) {
            char value = from;
            while (value <= to) {
                closure.call(value);
                value += step;
            }
        } else {
            char value = to;
            while (value >= from) {
                closure.call(value);
                value += step;
            }
        }
    }

    @Override
    public List<Character> step(final int step) {
        final IteratorClosureAdapter<Character> adapter = new IteratorClosureAdapter<>(this);
        step(step, adapter);
        return adapter.asList();
    }

    @Override
    public String inspect() {
        return reverse ?
               format("%s..%s", to, from) :
               format("%s..%s", from, to);
    }

    @Override
    public Character get(final int index) {
        assertIndexIsNonNegative(index);
        assertIndexLessThanSize(index);
        final int val = reverse ? to - index : from + index;
        return (char) val;
    }

    @Override
    public List<Character> subList(final int fromIndex, final int toIndex) {
        assertIndexIsNonNegative(fromIndex);
        assertIndexLessThanSize(toIndex);
        if (fromIndex == toIndex) {
            //noinspection unchecked
            return new EmptyRange(from);
        }
        final char sFrom = (char) (fromIndex + from);
        final char sTo = (char) (toIndex + from - 1);
        return new CharRange(sFrom, sTo, reverse);
    }

    @Override
    public int size() {
        return to - from + 1;
    }

    @Override
    public boolean contains(final Object o) {
        boolean contains = false;
        //noinspection ChainOfInstanceofChecks
        if (o instanceof Character) {
            final Character c = (Character) o;
            contains = c >= from && c <= to;
        } else if (o instanceof String) {
            final String s = (String) o;
            if (s.length() == 1) {
                //noinspection TailRecursion
                contains = contains(s.charAt(0));
            }
        }
        return contains;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        //noinspection InstanceofConcreteClass
        if (c instanceof CharRange) {
            //noinspection CastToConcreteClass
            final CharRange range = (CharRange) c;
            return from <= range.getFrom() && range.getTo() <= to;
        }
        return super.containsAll(c);
    }

    @SuppressWarnings("MethodMayBeStatic")
    private void assertIndexIsNonNegative(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(format("Index: %d should not be negative", index));
        }
    }

    private void assertIndexLessThanSize(final int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException(format("Index: %d is too big for range: %s", index, this));
        }
    }
}
