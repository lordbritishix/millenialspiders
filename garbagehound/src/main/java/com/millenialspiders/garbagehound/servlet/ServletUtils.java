package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;

class ServletUtils {
    static void writeSuccess(HttpServletResponse resp, boolean isSuccess) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");

        JsonObject ret = new JsonObject();
        ret.addProperty("success", isSuccess);

        out.write(ret.toString());
    }

    static void writeAccessControl(HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin","*");
        resp.addHeader("Access-Control-Allow-Methods","GET,POST,PUT");
        resp.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
    }
}
