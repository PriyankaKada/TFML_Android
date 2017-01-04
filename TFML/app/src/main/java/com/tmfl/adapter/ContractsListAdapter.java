package com.tmfl.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.billdesk.sdk.PaymentOptions;
import com.tmfl.BillDeskPayment.GetBillDeskMsg;
import com.tmfl.BillDeskPayment.SampleCallBack;
import com.tmfl.R;
import com.tmfl.activity.EmiActivity;
import com.tmfl.auth.Constant;
import com.tmfl.customview.MoreDetailDialog;
import com.tmfl.model.ContractResponseModel.ContractModel;
import com.tmfl.util.PreferenceHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ContractsListAdapter extends ArrayAdapter<ContractModel> {

    Context mContext;
    TextView txt_contract_no, txt_rc_no, txt_product_name, txt_current_emi_amount,
            txt_last_payment_date, txt_next_due_date, txt_overdue_amount, txt_repayment_mode;

    TextView txtContractNoTerm, txt_rc_no_term, txt_terminated_name, txt_terminated_date;
    TextView /*btn_pay_emi*/   btn_more_detail;
    ArrayList<ContractModel> arrayList;
    SimpleDateFormat dateFormat, format2;
    Date varDate;
    String strMsg ="TATAMFLTES|CO2017010452830|NA|1|NA|NA|NA|INR|NA|R|tatamfl|NA|NA|F|1234@100,45678@150|58724@190,45678@200|58724@220,45678@2500|58724@300,45678@350|NA|2002680054|9892827269|http://staging.php-dev.in:8844/tatamotors/public/api/billDeskResponse|7389D48DFAAA73CF3EBA440DBB6BDB272B9FFF4FCF7EB063558DAE3C69032D5B";
    //String strMsg="TATAMFL|CO2017010300001|NA|1|NA|NA|NA|INR|NA|R|tatamfl|NA|NA|F|1234@0,45678@0|58724@0,45678@0|58724@1,45678@0|58724@0,45678@0|NA|2002680054|9892827269|http:\\/\\/staging.php-dev.in:8844\\/tatamotors\\/public\\/customer\\/billDeskResponse|590DF0C2844D2118C4E117FA9FDFA4F50B996F9C4B0C9A96E52CD862D5BE9B34";
    String strEmail = "NA";
    String strMobile = "9892827269";
    private Button btn_pay_emi;
    private View viewBlueMark, viewGreenMark;

    public ContractsListAdapter(Context context, ArrayList<ContractModel> objects) {
        super(context, 0, objects);
        mContext = context;
        this.arrayList = objects;
    }

    public static String trimLeadingZeros(String source) {
        for (int i = 0; i < source.length(); ++i) {
            char c = source.charAt(i);
            if (c != '0' && !Character.isSpaceChar(c)) {
                return source.substring(i);
            }
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ContractModel model = getItem(position);
        if (model == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.header_terminated_contract, parent, false);
        } else if (model.getContractStatus().equalsIgnoreCase("L")) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_active_contract, parent, false);
            txt_product_name = (TextView) convertView.findViewById(R.id.txt_product_name);
            txt_contract_no = (TextView) convertView.findViewById(R.id.txt_contract_no);
            txt_rc_no = (TextView) convertView.findViewById(R.id.txt_rc_no);
            txt_current_emi_amount = (TextView) convertView.findViewById(R.id.txt_current_emi_amount);
            txt_next_due_date = (TextView) convertView.findViewById(R.id.txt_next_due_date);
            txt_overdue_amount = (TextView) convertView.findViewById(R.id.txt_overdue_amount);
            txt_repayment_mode = (TextView) convertView.findViewById(R.id.txt_repayment_mode);
            txt_last_payment_date = (TextView) convertView.findViewById(R.id.txt_last_payment_date);
            btn_pay_emi = (Button) convertView.findViewById(R.id.btn_pay_emi);
            btn_more_detail = (TextView) convertView.findViewById(R.id.btn_more_detail);

            viewBlueMark = convertView.findViewById(R.id.viewBlueMark);
            viewGreenMark = convertView.findViewById(R.id.viewGreenMark);

            if (model.getUsrConCompCode().equalsIgnoreCase("5000")) {
                viewGreenMark.setVisibility(View.VISIBLE);
                viewBlueMark.setVisibility(View.GONE);
            } else if (model.getUsrConCompCode().equalsIgnoreCase("8000")) {
                viewGreenMark.setVisibility(View.GONE);
                viewBlueMark.setVisibility(View.VISIBLE);
            }

            txt_product_name.setText(model.getProduct() == null ? "" : model.getProduct().toString());
            txt_contract_no.setText(model.getUsrConNo() == null ? "" : trimLeadingZeros(model.getUsrConNo()));
            txt_rc_no.setText(model.getRcNumber() == null ? "" : model.getRcNumber());
            txt_overdue_amount.setText(model.getOdAmt() == null ? "" : "Rs." + model.getOdAmt());
            txt_repayment_mode.setText(model.getPdcFlag() == null ? "" : model.getPdcFlag());
            txt_next_due_date.setText(model.getDueDate() == null ? "" : model.getDueDate().toString());
            txt_last_payment_date.setText(model.getLastReceiptDate() == null ? "" : model.getLastReceiptDate().toString());
            txt_current_emi_amount.setText(model.getDueAmount() == null ? "" : "Rs." + model.getDueAmount().toString());
            Log.e("DueAmount", model.getDueAmount().toString());
            Log.e("TDueAmount", txt_current_emi_amount.getText().toString());

            btn_more_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("arrayList---------->" + arrayList.size());
                    String senddatavalue = txt_contract_no.getText().toString();
                    String sendRcno = txt_rc_no.getText().toString();
                    String sendOverdueAmt = txt_overdue_amount.getText().toString();
                    String sendRepaymentMode = txt_repayment_mode.getText().toString();
                    String sendNextDueDate = txt_next_due_date.getText().toString();
                    String sendCurrentEmiAmt = txt_current_emi_amount.getText().toString();
                    String sendLastPay = txt_last_payment_date.getText().toString();

                    PreferenceHelper.insertObject(Constant.CONTRACT_DETAIL, model);

                    Intent intent = new Intent(mContext, EmiActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("datamodel", arrayList);
                    bundle.putString("datamodelvalue", model.getUsrConNo());
                    PreferenceHelper.insertString(PreferenceHelper.CONTRACT_NO, model.getUsrConNo());
                    bundle.putString("RCNO", model.getRcNumber());
                    bundle.putString("OVERDUEAMT", model.getOdAmt());
                    bundle.putString("REPAYMENT", model.getPdcFlag());
                    bundle.putString("DUEDATE", String.valueOf(model.getDueDate()));
                    bundle.putString("CURRENTEMI", String.valueOf(model.getDueAmount()));
                    bundle.putString("LASTPAYMODE", model.getLastReceiptDate());
                    intent.putExtras(bundle);

                    new MoreDetailDialog(mContext);
                }
            });
            btn_pay_emi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    SampleCallBack callbackObj = new SampleCallBack();
//                    Intent intent = new Intent(mContext,
//                            PaymentOptions.class);
//
//                    intent.putExtra("msg", strMsg); // pg_msg
////				intent.putExtra("token", strToken);
//                    intent.putExtra("user-email", strEmail);
//                    intent.putExtra("user-mobile", strMobile);
//                    intent.putExtra("callback", callbackObj);
//                    mContext.startActivity(intent);
                    new GetBillDeskMsg(mContext," [\"1234@100\",\"45678@150\",\"58724@190\",\"45678@200\",\"58724@220\",\"45678@2500\",\"58724@300\",\"45678@350\",\"58724@400\"]");


                }
            });

        } else if (model.getContractStatus().equalsIgnoreCase("T")) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_terminated_contract, parent, false);
            txtContractNoTerm = (TextView) convertView.findViewById(R.id.txtContractNoTerm);
            txt_rc_no_term = (TextView) convertView.findViewById(R.id.txt_rc_no_term);
            txt_terminated_name = (TextView) convertView.findViewById(R.id.txt_terminated_name);
            txt_terminated_date = (TextView) convertView.findViewById(R.id.txt_terminated_date);
            if (model.getContractStatusDate() != null) {
                txt_terminated_name.setText(model.getProduct() == null ? "" : model.getProduct().toString());
                txtContractNoTerm.setText(model.getUsrConNo() == null ? "" : trimLeadingZeros(model.getUsrConNo()));
                txt_rc_no_term.setText(model.getRcNumber() == null ? "" : model.getRcNumber().toString());

                dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                try {
                    if (model.getContractStatusDate() != null) {
                        varDate = dateFormat.parse(model.getContractStatusDate().toString());
                        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    }
                    txt_terminated_date.setText(model.getContractStatusDate() == null ? "" : "Terminated On " + dateFormat.format(varDate));

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }
        return convertView;
    }
}
