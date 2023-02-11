package com.example.androidfundamentals.fragment;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.helper.NotificationJobService;

public class NotificationSchedulerFragment extends Fragment
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private JobScheduler jobScheduler;
    private static final int JOB_ID = 0;
    private RadioGroup networkOptions;
    private Button scheduleJobBtn, cancelJobBtn;
    private Switch deviceIdleSwitch, deviceChargingSwitch;
    private SeekBar seekBar;
    private TextView seekBarProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_scheduler, container, false);
        scheduleJobBtn = view.findViewById(R.id.scheduleJob);
        cancelJobBtn = view.findViewById(R.id.cancelJobs);
        networkOptions = view.findViewById(R.id.radioGroup);
        deviceIdleSwitch = view.findViewById(R.id.deviceIdle);
        deviceChargingSwitch = view.findViewById(R.id.deviceCharging);
        seekBar = view.findViewById(R.id.seekBar);
        seekBarProgress = view.findViewById(R.id.seekBarProgress);
        scheduleJobBtn.setOnClickListener(this);
        cancelJobBtn.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.scheduleJob) {

            int selectedNetworkID = networkOptions.getCheckedRadioButtonId();
            int selectedNetworkOption = getSelectedNetwork(selectedNetworkID);
            int seekBarInteger = seekBar.getProgress();
            boolean seekBarSet = seekBarInteger > 0;
            jobScheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
            ComponentName componentName = new ComponentName(getContext().getPackageName(),
                    NotificationJobService.class.getName());

            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
            builder.setRequiredNetworkType(selectedNetworkOption);
            builder.setRequiresDeviceIdle(deviceIdleSwitch.isChecked());
            builder.setRequiresCharging(deviceChargingSwitch.isChecked());
            if (seekBarSet) {
                builder.setOverrideDeadline(seekBarInteger * 1000);
            }
            jobScheduler.schedule(builder.build());
            Toast.makeText(getContext(), "Job Scheduled, job will run when " +
                    "the constraints are met.", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.cancelJobs) {
            if (jobScheduler != null) {
                jobScheduler.cancelAll();
                jobScheduler = null;
                Toast.makeText(getContext(), "Jobs cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int getSelectedNetwork(int selectedNetworkID) {
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
        if (selectedNetworkID == R.id.anyNetwork) {
            selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
        } else if (selectedNetworkID == R.id.wifiNetwork) {
            selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
        }
        return selectedNetworkOption;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (i > 0) {
            seekBarProgress.setText(i + " s");
        } else {
            seekBarProgress.setText("Not Set");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}