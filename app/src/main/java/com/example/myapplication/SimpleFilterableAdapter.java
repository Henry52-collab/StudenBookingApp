package com.example.myapplication;

import android.content.Context;

import java.util.List;

public class SimpleFilterableAdapter<ObjectType> extends FilterableAdapter<ObjectType, String> {
    public SimpleFilterableAdapter(Context context) {
        super(context);
    }

    public SimpleFilterableAdapter(Context context, int resourceId) {
        super(context, resourceId);
    }

    public SimpleFilterableAdapter(Context context, List<ObjectType> objects) {
        super(context, objects);
    }

    public SimpleFilterableAdapter(Context context, int resourceId, List<ObjectType> objects) {
        super(context, resourceId, objects);
    }
    // (...inherited constructors...)

    @Override
    protected String prepareFilter(CharSequence seq) {

        /* The object we return here will be passed to passesFilter() as constraint.
         ** This method is called only once per filter run. The same constraint is
         ** then used to decide upon all objects in the data set.
         */

        return seq.toString();
    }

    @Override
    protected boolean passesFilter(ObjectType object, String constraint) {
        String repr = object.toString().toLowerCase();

        if (repr.startsWith(constraint.toLowerCase()))
            return true;

        else {
            final String[] words = repr.split(" ");
            final int wordCount = words.length;

            for (int i = 0; i < wordCount; i++) {
                if (words[i].startsWith(constraint.toLowerCase()))
                    return true;
            }
        }

        return false;
    }
}