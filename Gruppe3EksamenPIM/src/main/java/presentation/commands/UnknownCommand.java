/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author andre
 */
public class UnknownCommand extends Command{
    

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String msg = "Unknown command. Contact IT";
        throw new IllegalArgumentException(msg);
    }
    
}