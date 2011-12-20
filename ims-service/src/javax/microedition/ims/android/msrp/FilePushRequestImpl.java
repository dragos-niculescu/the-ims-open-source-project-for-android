/*
 * This software code is (c) 2010 T-Mobile USA, Inc. All Rights Reserved.
 *
 * Unauthorized redistribution or further use of this material is
 * prohibited without the express permission of T-Mobile USA, Inc. and
 * will be prosecuted to the fullest extent of the law.
 *
 * Removal or modification of these Terms and Conditions from the source
 * or binary code of this software is prohibited.  In the event that
 * redistribution of the source or binary code for this software is
 * approved by T-Mobile USA, Inc., these Terms and Conditions and the
 * above copyright notice must be reproduced in their entirety and in all
 * circumstances.
 *
 * No name or trademarks of T-Mobile USA, Inc., or of its parent company,
 * Deutsche Telekom AG or any Deutsche Telekom or T-Mobile entity, may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" AND "WITH ALL FAULTS" BASIS
 * AND WITHOUT WARRANTIES OF ANY KIND.  ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS OR WARRANTIES, INCLUDING ANY IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT CONCERNING THIS SOFTWARE, ITS SOURCE OR BINARY CODE
 * OR ANY DERIVATIVES THEREOF ARE HEREBY EXCLUDED.  T-MOBILE USA, INC.
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE
 * OR ITS DERIVATIVES.  IN NO EVENT WILL T-MOBILE USA, INC. OR ITS
 * LICENSORS BE LIABLE FOR LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT
 * OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF T-MOBILE USA,
 * INC. HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * THESE TERMS AND CONDITIONS APPLY SOLELY AND EXCLUSIVELY TO THE USE,
 * MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE, ITS SOURCE OR BINARY
 * CODE OR ANY DERIVATIVES THEREOF, AND ARE SEPARATE FROM ANY WRITTEN
 * WARRANTY THAT MAY BE PROVIDED WITH A DEVICE YOU PURCHASE FROM T-MOBILE
 * USA, INC., AND TO THE EXTENT PERMITTED BY LAW.
 */

package javax.microedition.ims.android.msrp;

import android.os.RemoteException;
import android.util.Log;

import javax.microedition.ims.core.dialog.Dialog;
import javax.microedition.ims.core.sipservice.Acceptable;
import javax.microedition.ims.messages.utils.StatusCode;

public class FilePushRequestImpl extends IFilePushRequest.Stub {
    private static final String TAG = "Service - FilePushRequestImpl";

    private final Dialog dialog;
    private final Acceptable acceptable;

    private final String requestId;
    private final String sender;
    private final String[] recipients;
    private final String subject;
    private final IFileInfo[] fileInfos;

    private boolean expired = false;

    public FilePushRequestImpl(Dialog dialog, Acceptable acceptable,
                               String requestId, String sender, String[] recipients, String subject,
                               IFileInfo[] fileInfos) {
        this.dialog = dialog;
        this.acceptable = acceptable;
        this.requestId = requestId;
        this.sender = sender;
        this.recipients = recipients;
        this.subject = subject;
        this.fileInfos = fileInfos;
    }

    
    public void accept() throws RemoteException {
        Log.i(TAG, "accept#started");

        acceptable.accept(dialog);
        expire();

        Log.i(TAG, "accept#finish");
    }

    
    public void reject() throws RemoteException {
        Log.i(TAG, "reject#started");

        acceptable.reject(dialog, StatusCode.TEMPORARY_UNAVAILABLE, "TEMPORARY_UNAVAILABLE");
        expire();

        Log.i(TAG, "reject#finish");
    }

    
    public String getRequestId() throws RemoteException {
        return requestId;
    }

    
    public String getSender() throws RemoteException {
        return sender;
    }

    
    public String[] getRecipients() throws RemoteException {
        return recipients;
    }

    
    public String getSubject() throws RemoteException {
        return subject;
    }

    
    public IFileInfo[] getFileInfos() throws RemoteException {
        return fileInfos;
    }

    
    public boolean isExpired() throws RemoteException {
        Log.i(TAG, "isExpired#called ... expired=" + expired);
        return expired;
    }

    void expire() {
        Log.i(TAG, "expire#called");
        expired = true;
    }

}
