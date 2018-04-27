package com.app.contact_loader.service;

import java.io.PrintWriter;

public interface ContactService {
    void getAll(String regex, PrintWriter printWriter);
}
