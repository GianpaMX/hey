package net.ddns.softux.hey;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ddns.softux.hey.databinding.TaskFragmentBinding;

/**
 * Created by juan on 5/06/16.
 */

public class TaskFragment extends DialogFragment {

    private static final String TAG = "TaskFragment";

    private static final int TASK_FRAGMENT_LAYOUT = R.layout.task_fragment;
    private static final String TASK = "TASK";

    private Task task;
    private NoticeDialogListener listener;

    public interface NoticeDialogListener<T extends DialogFragment> {
        void onDialogPositiveClick(T dialog);
        void onDialogNegativeClick(T dialog);
    }

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }

    public TaskFragment() {
    }

    public Task getTask() {
        return task;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof NoticeDialogListener) {
            listener = (NoticeDialogListener) activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            task = new Task();
        } else {
            task = savedInstanceState.getParcelable(TASK);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(TASK, task);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getShowsDialog()) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        View view = inflater.inflate(TASK_FRAGMENT_LAYOUT, container, false);
        bind(view);
        return view;
    }

    private void bind(View view) {
        TaskFragmentBinding binding = TaskFragmentBinding.bind(view);
        binding.setTask(task);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(TASK_FRAGMENT_LAYOUT, null);
        bind(view);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listener != null) {
                            listener.onDialogPositiveClick(TaskFragment.this);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listener != null) {
                            listener.onDialogNegativeClick(TaskFragment.this);
                        }
                    }
                })
                .create();
    }
}
