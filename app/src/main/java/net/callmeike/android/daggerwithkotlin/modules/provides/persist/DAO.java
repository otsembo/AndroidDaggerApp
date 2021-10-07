package net.callmeike.android.daggerwithkotlin.modules.provides.persist;

import android.util.Log;


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
public interface DAO {
    static DAO get() { return new DAOImpl(); }

    void store(Transaction transaction, String data);
}

class DAOImpl implements DAO {
    @Override
    public void store(final Transaction transaction, final String data) {
        Log.d("DAO", "@" + transaction + ", storing: " + data);
    }
}
