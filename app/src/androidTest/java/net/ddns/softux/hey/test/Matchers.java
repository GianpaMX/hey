package net.ddns.softux.hey.test;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by juan on 5/06/16.
 */

public class Matchers {
    public static Matcher<View> atPositionOnView(final int position, final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has view id " + itemMatcher + " at position " + position);
            }

            @Override
            public boolean matchesSafely(final RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);

                if(viewHolder == null) {
                    return false;
                }

                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
