package dev.whaabaam.com.data.model.other;
/*
 * Created by RahulGupta on 6/9/18
 */

import android.support.annotation.NonNull;

import com.quickblox.chat.model.QBChatDialog;

public class MyQBChatDialog extends QBChatDialog implements Comparable {


    @Override
    public int compareTo(@NonNull Object o) {
        MyQBChatDialog compare = (MyQBChatDialog) o;

        if (compare.getUpdatedAt().equals(this.updatedAt)
                && compare.getUnreadMessageCount().equals(this.getUnreadMessageCount())) {
            return 0;
        }
        return 1;
    }
}
