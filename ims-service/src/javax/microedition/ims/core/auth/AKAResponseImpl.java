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

package javax.microedition.ims.core.auth;

/**
 * User: Pavel Laboda (pavel.laboda@gmail.com)
 * Date: 1.7.2010
 * Time: 18.22.26
 */
public class AKAResponseImpl implements AKAResponse {
    private final byte[] ck;
    private final byte[] ik;
    private final byte[] auts;
    private final byte[] res;

    public AKAResponseImpl(byte[] ck, byte[] ik, byte[] auts, byte[] res) {
        this.ck = ck;
        this.ik = ik;
        this.auts = auts;
        this.res = res;
    }

    
    public byte[] getCk() {
        return ck;
    }

    
    public byte[] getIk() {
        return ik;
    }

    
    public byte[] getAuts() {
        return auts;
    }

    
    public byte[] getRes() {
        return res;
    }

    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AKAResponseImpl");
        sb.append("{ck=").append(ck == null ? "null" : "");
        for (int i = 0; ck != null && i < ck.length; ++i) {
            sb.append(i == 0 ? "" : ", ").append(ck[i]);
        }
        sb.append(", ik=").append(ik == null ? "null" : "");
        for (int i = 0; ik != null && i < ik.length; ++i) {
            sb.append(i == 0 ? "" : ", ").append(ik[i]);
        }
        sb.append(", auts=").append(auts == null ? "null" : "");
        for (int i = 0; auts != null && i < auts.length; ++i) {
            sb.append(i == 0 ? "" : ", ").append(auts[i]);
        }
        sb.append(", res=").append(res == null ? "null" : "");
        for (int i = 0; res != null && i < res.length; ++i) {
            sb.append(i == 0 ? "" : ", ").append(res[i]);
        }
        sb.append('}');
        return sb.toString();
    }
}
