/*******************************************************************************
 * Copyright (c) 2006-2007 Koji Hisano <hisano@gmail.com> - UBION Inc. Developer
 * Copyright (c) 2006-2007 UBION Inc. <http://www.ubion.co.jp/>
 * 
 * Copyright (c) 2006-2007 Skype Technologies S.A. <http://www.skype.com/>
 * 
 * Skype4Java is licensed under either the Apache License, Version 2.0 or
 * the Eclipse Public License v1.0.
 * You may use it freely in commercial and non-commercial products.
 * You may obtain a copy of the licenses at
 *
 *   the Apache License - http://www.apache.org/licenses/LICENSE-2.0
 *   the Eclipse Public License - http://www.eclipse.org/legal/epl-v10.html
 *
 * If it is possible to cooperate with the publicity of Skype4Java, please add
 * links to the Skype4Java web site <https://developer.skype.com/wiki/Java_API> 
 * in your web site or documents.
 * 
 * Contributors: Koji Hisano - initial API and implementation
 ******************************************************************************/
package com.skype.sample;

import com.skype.ContactList;
import com.skype.Friend;
import com.skype.Skype;

public class MakeCall {
    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.out.println("Usage: java com.skype.sample.MakeCall 'skype_id'");
//            return;
//        }
//        Skype.call(args[0]);
//        Skype.call("John Brown");
//        Skype.call("facebook:aruyig");

        //Getting all the contact list for log in Skype
        ContactList list = Skype.getContactList();
        Friend fr[] = list.getAllFriends();
        //Printing the no of friends Skype have
        System.out.println(fr.length);

        //Iterating through friends list
        for(int i=0; i < fr.length; i++)
        {
            Friend f = fr[i];
            //Getting the friend ID
            System.out.println("Friend ID :"+f.getId());
//            Thread.sleep(100);
        }

        Skype.call("facebook:aruyig");

    }
}
