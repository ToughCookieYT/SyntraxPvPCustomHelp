package com.gmail.ToughCookie.SPCustomHelp;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PaginatedMessageList {
    private String header = "";
    private String footer = "";
    private int entriesPerPage = 8;

    public PaginatedMessageList(String header, String footer, int entriesPerPage) {
        this.setHeader(header);
        this.setFooter(footer);
        this.setEntriesPerPage(entriesPerPage);
    }

    public PaginatedMessageList(String header, String footer) {
        this.setHeader(header);
        this.setFooter(footer);
    }

    public PaginatedMessageList(int entriesPerPage) {
        this.setEntriesPerPage(entriesPerPage);
    }

    public PaginatedMessageList() {
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void setEntriesPerPage(int entriesPerPage) {
        this.entriesPerPage = entriesPerPage;
    }

    public void send(CommandSender sender, ArrayList<String> list, int page) {
        if (list.size() == 0) {
            return;
        }
        int pages = list.size() / this.entriesPerPage;
        if (list.size() % this.entriesPerPage == 0) {
            --pages;
        }
        page = Math.max(0, Math.min(page, pages));
        sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)this.header.replace("{page}", "" + (page + 1)).replace("{maxpages}", "" + (pages + 1)).replace("{nextpage}", "" + (page + 2))));
        int i = this.entriesPerPage * page;
        while (i < this.entriesPerPage * page + this.entriesPerPage && i < list.size()) {
            sender.sendMessage(list.get(i));
            ++i;
        }
        if (pages >= 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)this.footer.replace("{page}", "" + (page + 1)).replace("{maxpages}", "" + (pages + 1)).replace("{nextpage}", "" + (page + 2))));
        }
    }
}

