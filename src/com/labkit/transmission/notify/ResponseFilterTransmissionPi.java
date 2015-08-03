/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labkit.transmission.notify;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

/**
 *
 * @author VDE
 */
public class ResponseFilterTransmissionPi implements ClientResponseFilter {

    @Override
    public void filter(final ClientRequestContext reqCtx,
            final ClientResponseContext resCtx) throws IOException {
        System.out.println("status: " + resCtx.getStatus());
        System.out.println("date: " + resCtx.getDate());
        System.out.println("last-modified: " + resCtx.getLastModified());
        System.out.println("location: " + resCtx.getLocation());
        System.out.println("headers:");
        for (Entry<String, List<String>> header : resCtx.getHeaders()
                .entrySet()) {
            System.out.print("\t" + header.getKey() + " :");
            for (String value : header.getValue()) {
                System.out.print(value + ", ");
            }
            System.out.print("\n");
        }
        System.out.println("media-type: " + resCtx.getMediaType().getType());
    }

}
