package com.rewaa.nearpay.capacitor;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.List;
import java.util.Locale;

import io.nearpay.sdk.Environments;
import io.nearpay.sdk.NearPay;
import io.nearpay.sdk.data.models.ReconciliationReceipt;
import io.nearpay.sdk.data.models.TransactionReceipt;
import io.nearpay.sdk.utils.enums.PurchaseFailure;
import io.nearpay.sdk.utils.enums.ReconcileFailure;
import io.nearpay.sdk.utils.enums.RefundFailure;
import io.nearpay.sdk.utils.enums.ReversalFailure;
import io.nearpay.sdk.utils.enums.SetupFailure;
import io.nearpay.sdk.utils.enums.StatusCheckError;
import io.nearpay.sdk.utils.listeners.PurchaseListener;
import io.nearpay.sdk.utils.listeners.ReconcileListener;
import io.nearpay.sdk.utils.listeners.RefundListener;
import io.nearpay.sdk.utils.listeners.ReversalListener;
import io.nearpay.sdk.utils.listeners.SetupListener;

@CapacitorPlugin(name = "RewaaNearpay")
public class RewaaNearpayPlugin extends Plugin {
  private RewaaNearpay implementation = new RewaaNearpay();
  private Context mContext;
  private String TAG = "RewaaNearpayPlugin";
  private NearPay nearPay;

  @Override
  public void load() {
    super.load();
    this.mContext = getContext();
  }

  @PluginMethod
  public void echo(PluginCall call) {
    String value = call.getString("value");

    JSObject ret = new JSObject();
    ret.put("value", implementation.echo(value));
    call.resolve(ret);
  }

  @PluginMethod
  public void initNearpay(PluginCall call) {
    String jwt = call.getString("token");
    JSObject ret = new JSObject();
    if (!TextUtils.isEmpty(jwt)) {
      nearPay = new NearPay(mContext, jwt, Locale.getDefault(), Environments.SANDBOX);
      if(nearPay != null)
        ret.put("status", true);
      else
        ret.put("status", false);
    } else {
      ret.put("status", false);
    }
    call.resolve(ret);
  }


  @PluginMethod
  public void setupNearpay(PluginCall call) {
    String jwt = call.getString("token");
    nearPay.setup(new SetupListener() {
      @Override
      public void onSetupCompleted() {
        Log.i("onSetupCompleted","setup is done successfully");
        JSObject ret = new JSObject();
        ret.put("isSetupComplete", true);
        ret.put("status_msg", "setup is done successfully");
        call.resolve(ret);
      }

      @Override
      public void onSetupFailed(@NonNull SetupFailure setupFailure) {
        int onSetupFailed = Log.e("onSetupFailed", String.valueOf(setupFailure));
        String type = "";
        if (setupFailure instanceof SetupFailure.AlreadyInstalled) {
          // when the payment plugin is already installed  .
          type = "AlreadyInstalled";
        }
        else if (setupFailure instanceof SetupFailure.NotInstalled){
          // when the installtion failed .
          type = "NotInstalled";
        }
        else if (setupFailure instanceof SetupFailure.AuthenticationFailed){
          // when the Authentication Failed.
          type = "AuthenticationFailed";
          nearPay.updateJwt(jwt);
        }
        else if (setupFailure instanceof SetupFailure.InvalidStatus){
          // you can get the status using the following code
          List<StatusCheckError> status = ((SetupFailure.InvalidStatus) setupFailure).getStatus();
          type = "InvalidStatus";
        }
        JSObject ret = new JSObject();
        ret.put("isSetupComplete", false);
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }

  @PluginMethod
  public void purchase(PluginCall call) {
    Integer amount = call.getInt("amount");
    Long amt = new Long(amount);
//        String crn = call.getString("crn");
    String crn = "";
    String jwt = call.getString("token");
    Boolean enableReceiptUi = true;

    nearPay.purchase(amt, crn, enableReceiptUi, new PurchaseListener() {
      @Override
      public void onPurchaseApproved(@Nullable List<TransactionReceipt> list) {
        Log.i("purchaseReceipt", String.valueOf(list.get(0).getQr_code()));
        Log.i("getStatus_message",list.get(0).getStatus_message().toString());
        JSObject ret = new JSObject();
        ret.put("paymentStatus", true);
        ret.put("crn", list.get(0).getPayment_account_reference());
        ret.put("reference_retrieval", list.get(0).getRetrieval_reference_number());
        ret.put("uuid", list.get(0).getTransaction_uuid());
        ret.put("status_msg", list.get(0).getStatus_message());
        ret.put("tid", list.get(0).getTid());
        ret.put("is_approved", list.get(0).is_approved());
        ret.put("purchaseReceipt", list.get(0).getQr_code());
        call.resolve(ret);
      }

      @Override
      public void onPurchaseFailed(@NonNull PurchaseFailure purchaseFailure) {
        Log.e("onPurchaseFailed",purchaseFailure.toString());
        String type = "";
        if (purchaseFailure instanceof PurchaseFailure.GeneralFailure) {
          Log.e("purchaseFailure","GeneralFailure");
          type = "GeneralFailure";
        } else if (purchaseFailure instanceof PurchaseFailure.PurchaseDeclined){
          Log.e("purchaseFailure","PurchaseDeclined");
          type = "PurchaseDeclined";
        } else if (purchaseFailure instanceof PurchaseFailure.PurchaseRejected){
          Log.e("purchaseFailure","PurchaseRejected");
          type = "PurchaseRejected";
        } else if (purchaseFailure instanceof PurchaseFailure.AuthenticationFailed){
          Log.e("purchaseFailure","AuthenticationFailed");
          type = "AuthenticationFailed";
          nearPay.updateJwt(jwt);
        } else if (purchaseFailure instanceof PurchaseFailure.InvalidStatus){
          // you can get the status using the following code
          List<StatusCheckError> status = ((PurchaseFailure.InvalidStatus) purchaseFailure).getStatus();
          Log.e("purchaseFailure","InvalidStatus "+status.toString());
          type = "InvalidStatus";
        }
        JSObject ret = new JSObject();
        ret.put("paymentStatus", false);
        ret.put("crn", crn);
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }

  @PluginMethod
  public void reconcile(PluginCall call) {
    Boolean enableReceiptUi = true;
    String jwt = call.getString("token");
    nearPay.reconcile(enableReceiptUi, new ReconcileListener() {
      @Override
      public void onReconcileFinished(@Nullable ReconciliationReceipt reconciliationReceipt) {
        Log.i("onreconcileApproved",reconciliationReceipt.toString());
        JSObject ret = new JSObject();
        ret.put("reconcileStatus", true);
        ret.put("reconciliationReceipt", reconciliationReceipt.getQr_code());
        call.resolve(ret);
      }

      @Override
      public void onReconcileFailed(@NonNull ReconcileFailure reconcileFailure) {
        Log.e("reconcileFailure",reconcileFailure.toString());
        String type = "";
        if (reconcileFailure instanceof ReconcileFailure.FailureMessage) {
          Log.e("reconcileFailure","GeneralFailure");
          type = "GeneralFailure";
        } else if (reconcileFailure instanceof ReconcileFailure.AuthenticationFailed){
          Log.e("reconcileFailure","AuthenticationFailed");
          type = "AuthenticationFailed";
          nearPay.updateJwt(jwt);
        } else if (reconcileFailure instanceof ReconcileFailure.InvalidStatus){
          Log.e("reconcileFailure","InvalidStatus");
          type = "InvalidStatus";
        } else if (reconcileFailure instanceof ReconcileFailure.GeneralFailure){
          Log.e("reconcileFailure","GeneralFailure");
          type = "GeneralFailure";
        }
        JSObject ret = new JSObject();
        ret.put("reconcileStatus", false);
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }

  @PluginMethod
  public void refund(PluginCall call) {
    Integer amount = call.getInt("amount");
    String jwt = call.getString("token");
    String transactionReferenceRetrievalNumber = call.getString("transactionReferenceRetrievalNumber");
    Integer customerReferenceNumber = call.getInt("customerReferenceNumber");
    Boolean enableReceiptUi = true;

    nearPay.refund(amount, transactionReferenceRetrievalNumber, String.valueOf(customerReferenceNumber), enableReceiptUi, new RefundListener() {
      @Override
      public void onRefundApproved(@Nullable List<TransactionReceipt> list) {
        Log.i("refundReceipt", String.valueOf(list.get(0).getQr_code()));
        JSObject ret = new JSObject();
        ret.put("refundStatus", true);
        ret.put("refundReceipt", list.get(0).getQr_code());
        call.resolve(ret);
      }

      @Override
      public void onRefundFailed(@NonNull RefundFailure refundFailure) {
        Log.e("RefundFailure", refundFailure.toString());
        String type = "";
        if (refundFailure instanceof RefundFailure.RefundDeclined) {
          Log.e("refundFailure", "RefundDeclined");
          type = "RefundDeclined";
        } else if (refundFailure instanceof RefundFailure.RefundRejected) {
          Log.e("refundFailure", "RefundRejected");
          type = "RefundRejected";
        } else if (refundFailure instanceof RefundFailure.AuthenticationFailed) {
          Log.e("refundFailure", "AuthenticationFailed");
          type = "AuthenticationFailed";
          nearPay.updateJwt(jwt);
        } else if (refundFailure instanceof RefundFailure.GeneralFailure) {
          Log.e("refundFailure", "GeneralFailure");
          type = "GeneralFailure";
        } else if (refundFailure instanceof RefundFailure.InvalidStatus) {
          Log.e("refundFailure", "InvalidStatus");
          type = "InvalidStatus";
        }
        JSObject ret = new JSObject();
        ret.put("refundStatus", false);
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }

  @PluginMethod
  public void reverse(PluginCall call) {
    String transactionUuid = call.getString("transactionUuid");
    Boolean enableReceiptUi = true;
    String jwt = call.getString("token");
    nearPay.reverse(transactionUuid, enableReceiptUi, new ReversalListener() {
      @Override
      public void onReversalFinished(@Nullable List<TransactionReceipt> list) {
        JSObject ret = new JSObject();
        ret.put("reverseStatus", true);
        call.resolve(ret);
      }

      @Override
      public void onReversalFailed(@NonNull ReversalFailure reversalFailure) {
        Log.e("reversalFailure", reversalFailure.toString());
        String type = "";
        if (reversalFailure instanceof ReversalFailure.AuthenticationFailed) {
          Log.e("reversalFailure", "AuthenticationFailed");
          type = "AuthenticationFailed";
          nearPay.updateJwt(jwt);
        } else if (reversalFailure instanceof ReversalFailure.GeneralFailure) {
          Log.e("reversalFailure", "GeneralFailure");
          type = "GeneralFailure";
        } else if (reversalFailure instanceof ReversalFailure.FailureMessage) {
          Log.e("reversalFailure", "FailureMessage");
          type = "FailureMessage";
        } else if (reversalFailure instanceof ReversalFailure.InvalidStatus) {
          Log.e("reversalFailure", "InvalidStatus");
          type = "InvalidStatus";
        }
        JSObject ret = new JSObject();
        ret.put("reverseStatus", false);
        ret.put("error_type", type);
        call.resolve(ret);
      }
    });
  }

  public void sendEvent(JSObject data) {
    Log.e("percent", String.valueOf(data));
    notifyListeners("downloadProgressChange", data);
  }

}
