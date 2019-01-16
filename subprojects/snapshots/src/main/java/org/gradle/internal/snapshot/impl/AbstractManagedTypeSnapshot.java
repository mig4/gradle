/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.snapshot.impl;

import com.google.common.collect.ImmutableList;
import org.gradle.internal.hash.Hashable;
import org.gradle.internal.hash.Hasher;

public class AbstractManagedTypeSnapshot<T extends Hashable> implements Hashable {
    protected final ImmutableList<T> state;

    public AbstractManagedTypeSnapshot(ImmutableList<T> state) {
        this.state = state;
    }

    public ImmutableList<T> getState() {
        return state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        AbstractManagedTypeSnapshot other = (AbstractManagedTypeSnapshot) obj;
        return state.equals(other.state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public void appendToHasher(Hasher hasher) {
        for (T element : state) {
            element.appendToHasher(hasher);
        }
    }
}
