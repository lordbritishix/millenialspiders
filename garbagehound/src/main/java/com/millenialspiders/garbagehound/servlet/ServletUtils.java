package com.millenialspiders.garbagehound.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;

class ServletUtils {
    static void writeSuccess(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");

        JsonObject ret = new JsonObject();
        ret.addProperty("success", true);

        out.write(ret.toString());
    }
}