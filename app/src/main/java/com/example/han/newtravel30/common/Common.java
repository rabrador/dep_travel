package com.example.han.newtravel30.common;

import com.example.han.newtravel30.Holder.QBUsersHolder;
import com.quickblox.users.model.QBUser;

import java.util.List;

/**
 * Created by user on 2017/5/6.
 */

public class Common {

    public static final String DIALOG_EXTRA = "Dialogs";

    public static String createChatDialogName(List<Integer> qbUsers) {
        List<QBUser> qbUsers1 = QBUsersHolder.getInstance().getUsersByIds(qbUsers);
        StringBuilder name = new StringBuilder();
        for (QBUser user : qbUsers1)
            name.append(user.getFullName()).append(" ");
        if (name.length() > 30)
            name = name.replace(30, name.length() - 1, "...");
        return name.toString();
    }
}
