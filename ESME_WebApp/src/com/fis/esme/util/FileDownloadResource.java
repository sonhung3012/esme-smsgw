package com.fis.esme.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.vaadin.Application;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.FileResource;

public class FileDownloadResource extends FileResource
{
	public FileDownloadResource(File sourceFile, Application application)
	{
		super(sourceFile, application);
	}
	
	public DownloadStream getStream()
	{
		try
		{
			final DownloadStream ds = new DownloadStream(new FileInputStream(getSourceFile()), getMIMEType(), getFilename());
			ds.setParameter("Content-Disposition", "attachment; filename="+ getFilename());
			ds.setCacheTime(getCacheTime());
			return ds;
		}
		catch (final FileNotFoundException e)
		{
			return null;
		}
	}
}
