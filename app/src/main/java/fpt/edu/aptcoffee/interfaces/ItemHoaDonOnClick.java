package fpt.edu.aptcoffee.interfaces;

import android.view.View;

import fpt.edu.aptcoffee.model.HangHoa;
import fpt.edu.aptcoffee.model.HoaDon;
import fpt.edu.aptcoffee.model.HoaDonAPI;
import fpt.edu.aptcoffee.model.InvoiceData;

public interface ItemHoaDonOnClick {
    void itemOclick(View view, HoaDonAPI hoaDon);
}
