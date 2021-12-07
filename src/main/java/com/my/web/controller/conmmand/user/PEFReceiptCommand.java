package com.my.web.controller.conmmand.user;

import com.my.db.entity.Transaction;
import com.my.db.entity.User;
import com.my.messages.MessageType;
import com.my.messages.Messages;
import com.my.services.TransactionService;
import com.my.util.PDFGenerator;
import com.my.web.controller.util.Path;
import com.my.web.controller.conmmand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PEFReceiptCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Messages messages = new Messages((String)session.getAttribute("lang"));
        User user = (User)session.getAttribute("user");

        int id = Integer.parseInt(req.getParameter("id"));
        Transaction transaction = TransactionService.get(id);

        if (TransactionService.userHasTransaction(user, transaction)) {
            session.setAttribute(MessageType.SUCCESS, messages.getPDFReceiptSuccess());

            ByteArrayOutputStream outputStream = PDFGenerator.createPDFDocument(transaction);
            resp.addHeader("Content-Type", "application/force-download");
            resp.addHeader("Content-Disposition", "attachment; filename=\"receipt.pdf\"");
            resp.getOutputStream().write(outputStream.toByteArray());
        } else {
            session.setAttribute(MessageType.ERROR, messages.getPDFReceiptError());
            resp.sendRedirect(Path.USER_TRANSACTIONS);
        }
    }
}
