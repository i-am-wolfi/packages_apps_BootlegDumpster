/*
 * Copyright (C) 2025 The BootleggersROM Project
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

package com.bootleggers.dumpster.external.themebox;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bootleggers.dumpster.R;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Generic single-category overlay picker. Shows every overlay registered
 * for (category, target) as a row ("Default" = stock) and enables the
 * tapped one exclusively. Subclasses only provide category/target/title.
 */
public abstract class BaseThemePicker extends PreferenceFragmentCompat {

    protected abstract String category();

    protected abstract String targetPackage();

    protected abstract int titleRes();

    protected ThemeHelper mThemeHelper;
    protected List<String> mPkgs;
    protected RecyclerView mRecyclerView;

    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final AtomicBoolean mApplying = new AtomicBoolean(false);

    @Override
    public void onCreatePreferences(Bundle b, String s) {
        mThemeHelper = new ThemeHelper(requireContext());
        mPkgs = mThemeHelper.getOverlayPackagesForCategory(category(), targetPackage());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().setTitle(titleRes());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_view, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mRecyclerView.setAdapter(new Adapter(requireContext()));
        return view;
    }

    /** Apply the tapped package. Subclasses (UIStyles trio) may extend. */
    protected void applyOverlay(String pkg) {
        mThemeHelper.setOverlayEnabled(category(), pkg, targetPackage());
    }

    protected class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
        private final Context mContext;
        private String mSelectedPkg;

        Adapter(Context context) {
            mContext = context;
            mSelectedPkg = mThemeHelper.getEnabledPkg(category(), targetPackage());
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fonts_option, parent, false);
            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            String pkg = mPkgs.get(position);
            holder.title.setText(mThemeHelper.getLabel(pkg));
            holder.label.setVisibility(View.GONE);
            holder.itemView.setActivated(pkg.equals(mSelectedPkg));
            holder.itemView.setOnClickListener(v -> {
                if (mApplying.get()) return;
                mApplying.set(true);
                String prev = mSelectedPkg;
                mSelectedPkg = pkg;
                updateActivated(prev, false);
                updateActivated(pkg, true);
                mExecutor.execute(() -> {
                    try {
                        applyOverlay(pkg);
                    } finally {
                        mHandler.post(() -> mApplying.set(false));
                    }
                });
            });
        }

        @Override
        public int getItemCount() {
            return mPkgs.size();
        }

        private void updateActivated(String pkg, boolean activated) {
            int index = mPkgs.indexOf(pkg);
            if (index < 0 || mRecyclerView == null) return;
            RecyclerView.ViewHolder holder =
                    mRecyclerView.findViewHolderForAdapterPosition(index);
            if (holder != null && holder.itemView != null) {
                holder.itemView.setActivated(activated);
            }
        }

        class Holder extends RecyclerView.ViewHolder {
            final TextView title;
            final TextView label;

            Holder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.option_title);
                label = itemView.findViewById(R.id.option_label);
            }
        }
    }
}
