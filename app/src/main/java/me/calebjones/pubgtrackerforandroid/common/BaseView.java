/*
 * Copyright 2016, The Android Open Source Project
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

package me.calebjones.pubgtrackerforandroid.common;

import android.os.Bundle;
import android.view.View;

public interface BaseView<T> {

    /*
    Get the root android view which is used by android mvp to present the view to the user
    this rootview is then used by the presenter
    to modify the root view or its child views
     */
    View getRootView();

    /*
    This method aggravates all the information about the mvp view
    which can be used by presenter
    by using the saved states in scenarios of android life cycle events
     */
    Bundle getViewState();

    void setPresenter(T presenter);

}
