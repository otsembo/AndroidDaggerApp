package net.callmeike.android.daggerwithkotlin.modules.provides.persist;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/* $Id: $
   Copyright 2018, G. Blake Meike

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
public final class Transaction {
    private final long ts;

    public Transaction(long ts) { this.ts = ts; }

    @Override
    public String toString() { return String.valueOf(ts); }
}