package com.fis.esme.core.services.scheduler.entity;

/*
 * cron4j - A pure Java cron-like scheduler
 * 
 * Copyright (C) 2007-2010 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License 2.1 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
import it.sauronsoftware.cron4j.SchedulerListener;
import it.sauronsoftware.cron4j.TaskExecutor;

/**
 * A very simple SchedulerListener, sending messages to the console.
 */
public class SchedulerTaskListener implements SchedulerListener {

	public void taskLaunching(TaskExecutor executor) {
		SchedulerTask task = (SchedulerTask) executor.getTask();
		task.updateSchedulerInProgress();
		System.out.println("Task scheduler for backup firewall launched!");
	}

	public void taskSucceeded(TaskExecutor executor) {
		try {
			SchedulerTask task = (SchedulerTask) executor.getTask();
			task.updateSchedulerDone();
			System.out.println("Task scheduler for backup firewall completed!");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void taskFailed(TaskExecutor executor, Throwable exception) {
		SchedulerTask task = (SchedulerTask) executor.getTask();
		task.updateSchedulerFail();
		System.out.println("Task failed due to an exception!");
		exception.printStackTrace();
	}

}
