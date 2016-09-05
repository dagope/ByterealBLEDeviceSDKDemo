package com.bytereal.byterealsdkdemo;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class AppPermissions {

    private static final String TAG = "AppPermisions";
    public static final String[] permissionsApp = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            //Manifest.permission.READ_PHONE_STATE,
            //Manifest.permission.GET_ACCOUNTS,
    };

    public static boolean checkAllPermissions(Context context){
        boolean rto = true;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {

            rto = checkPermision(context, permissionsApp);
        }
        return rto;
    }

    public static boolean shouldShowAnyRequestPermisions(Activity activity, String[] manifiestPermissions){
        boolean rto = false;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            for (String mPermisions :manifiestPermissions) {
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity, mPermisions) == true){
                    rto = true;
                }
            }
        }
        return rto;
    }

    public static Integer getResourcesReasonRationalePermissions(String manifiestPermission){
        Integer rto = null;
        if(manifiestPermission.equals(Manifest.permission.ACCESS_FINE_LOCATION)){
            rto = R.string.permissions_location_rationale;
        }
        else if(manifiestPermission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)){
            rto = R.string.permissions_location_rationale;
        }

        return rto;
    }

    public static void showDialogAllRequestPermissions(final Activity activity, String[] manifiestPermissions , DialogInterface.OnClickListener okListener) {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {

            ArrayList<Integer> reasonsPermisions = new ArrayList<Integer>();
            for (String mPermisions :manifiestPermissions) {
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity, mPermisions) == true){
                    Integer resource = getResourcesReasonRationalePermissions(mPermisions);
                    if (reasonsPermisions.contains(resource) == false) {
                        reasonsPermisions.add(resource);
                    }
                }
            }

            String messageReasons = activity.getString(R.string.permissions_rationale_messageOfList);
            for (Integer iResource :reasonsPermisions ) {
                messageReasons += "\n- " +  activity.getString(iResource);
            }
            messageReasons += "\n\n" +  activity.getString(R.string.permissions_rationale_toInitApp);



            AlertDialog.Builder alertBuilder= new AlertDialog.Builder(activity)
                    .setMessage(messageReasons)
                    .setPositiveButton(activity.getString(R.string.ok_button_showMessage), okListener)
                    .setNegativeButton(activity.getString(R.string.no_and_exit_button_showMessage), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    })
                    //.setItems(stringReasons, null)
                    ;

            alertBuilder.create().show();
        }
    }


    public static boolean checkPermision(Context context,  String[] manifiestPermissions){
        boolean hasPermission = true;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            for (String mPermisions : manifiestPermissions) {
                if (ActivityCompat.checkSelfPermission(context ,mPermisions) != PackageManager.PERMISSION_GRANTED) {
                    hasPermission = false;
                }
            }
        }
        return hasPermission;
    }

    public static void requirePermissions(Activity activity, String[] manifiestPermissions, int myCodePermision ){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, manifiestPermissions, myCodePermision);
        }
    }


    public static boolean requirePermisionToUseApp(Context context, Activity activity, String[] manifiestPermissions, int iString_reason_permisions, int myCodePermision){
        boolean hasPermission = true;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            hasPermission = checkPermision(context, manifiestPermissions);
            if(hasPermission == false){
                boolean shouldShowRequestPermision = false;
                for (String mPermisions :manifiestPermissions) {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(activity, mPermisions) == true){
                        shouldShowRequestPermision = true;
                    }
                }
                if(shouldShowRequestPermision){
                    showMessageOKCancel(activity, activity.getString(iString_reason_permisions), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //ActivityCompat.requestPermissions(this, manifiestPermissions, myCodePermision);
                            //hasPermission = checkPermision(manifiestPermissions);
                        }
                    });
                }
                else{
                    ActivityCompat.requestPermissions(activity, manifiestPermissions, myCodePermision);
                    hasPermission = checkPermision(context, manifiestPermissions);
                }
            }
        }

        for (String mPermisions : manifiestPermissions) {
            Log.i(TAG,"Permisos para " + mPermisions + " = " + String.valueOf(hasPermission) );
        }

        return hasPermission;
    }


    private static void showMessageOKCancel(Activity activity, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.ok_button_showMessage), okListener)
                .setNegativeButton(activity.getString(R.string.cancel_button_showMessage), null)
                .create()
                .show();
    }

}
