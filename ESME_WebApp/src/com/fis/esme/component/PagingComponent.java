package com.fis.esme.component;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.fis.esme.util.MCATheme;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import eu.livotov.tpt.i18n.TM;

/**
 * This class creates a layout to navigate in different pages of results, the
 * layout contains a button "First", "Last", "Previous", "Next" and as many
 * buttons (called button pages) as the variable "numberOfButtonsPage". Each
 * button has a caption as page number which is updated when navigating.
 */
@SuppressWarnings("serial")
public class PagingComponent extends HorizontalLayout implements
		Button.ClickListener
{
	
	private int numberOfButtonsPage; // Only ODD numbers (not 10, but 9 or 11
										// for example), else the algo breaks
										// (because we put the current page in
										// the middle).
	private int buttonPageMargin, numberTotalOfPages, numberOfResults,
			hitsPerPage, currentPageOfResults;
	private List<ButtonPageNavigator> listButtons;// button corresponding to
													// pages
	private Button buttonPrevious, buttonNext, buttonFirst, ButtonLast;
//	private List<E> itemsList; // List with all items.
	private HorizontalLayout buttonsPageLayout;
	private List<String> stylesButtonNormal, stylesButtonCurrent;
	
	// Necessary for the listener (code copy/pasted from Vaadin's
	// ButtonClickListener, looks heavy but necessary for makeing it work with
	// anonymous Listeners)
	public static final Method METHOD_DISPLAY_PAGE;
	static
	{
		try
		{
			METHOD_DISPLAY_PAGE = PagingComponentListener.class
					.getDeclaredMethod("displayPage",
							new Class[]{ ChangePageEvent.class });
		}
		catch (final java.lang.NoSuchMethodException e)
		{
			// This should never happen
			throw new java.lang.RuntimeException(
					"Internal error finding methods in PagingComponent", e);
		}
	}
	
	public PagingComponent(int hitsPerPage, int totalRecord,
			PagingComponentListener pagingComponentListener)
	{
		this(hitsPerPage, 9, totalRecord, pagingComponentListener);
	}
	
	public PagingComponent(int hitsPerPage, int numberOfButtonsPage,
			int totalRecord,
			PagingComponentListener pagingComponentListener)
	{
		this.setSpacing(true);
		
		addListener(pagingComponentListener);
		
		stylesButtonNormal = new ArrayList<String>();
		stylesButtonCurrent = new ArrayList<String>();
		
		this.hitsPerPage = hitsPerPage;
		this.numberOfButtonsPage = numberOfButtonsPage;
		calculateButtonPageMargin();
		
		currentPageOfResults = 1;
//		itemsList = new ArrayList<E>(itemsCollection);
//		numberOfResults = itemsCollection.size();
		numberOfResults = totalRecord;
		numberTotalOfPages = (int) Math.ceil((double) numberOfResults
				/ hitsPerPage); // Calculate the number of pages needed: if
								// there are 105 results and we want 10
								// results/page --> we will get 11 pages
		
		listButtons = new ArrayList<ButtonPageNavigator>();
		buttonsPageLayout = new HorizontalLayout();
		buttonsPageLayout.setSpacing(true);
		
		// create buttons of the navigator
		if (numberTotalOfPages == 1)
		{
			// example -1-
			createButtonsPage();
			setStyleNameForAllButtons(MCATheme.BUTTON_PAGER);
			
			this.addComponent(new Label("-"));
			this.addComponent(buttonsPageLayout);
			this.addComponent(new Label("-"));
			
		}
		else
		{
			buttonFirst = new ButtonNavigator(TM.get("pager.page.first.caption"), this);
			buttonPrevious = new ButtonNavigator(TM.get("pager.page.previous.caption"), this);
			createButtonsPage();
			buttonNext = new ButtonNavigator(TM.get("pager.page.next.caption"), this);
			ButtonLast = new ButtonNavigator(TM.get("pager.page.last.caption"), this);
			
			setStyleNameForAllButtons(MCATheme.BUTTON_PAGER);
			
			this.addComponent(buttonFirst);
			this.addComponent(buttonPrevious);
			this.addComponent(new Label("-"));
			this.addComponent(buttonsPageLayout);
			this.addComponent(new Label("-"));
			this.addComponent(buttonNext);
			this.addComponent(ButtonLast);
		}
		
		// throw event for PagingComponentListener and update for the first time
		// RangeDisplayer
		runChangePageEvent();
	}

	@SuppressWarnings("unchecked")
	public void buttonClick(ClickEvent event)
	{
		int previousPage;
		Button buttonPressed = event.getButton();
		
		if (buttonPressed == buttonFirst)
		{
			previousPage = currentPageOfResults;
			if (currentPageOfResults != 1)
			{
				currentPageOfResults = 1;
				reorganizeButtonsPageNavigator(previousPage);
				runChangePageEvent();
			}
		}
		else if (buttonPressed == ButtonLast)
		{
			previousPage = currentPageOfResults;
			if (currentPageOfResults != numberTotalOfPages)
			{
				currentPageOfResults = numberTotalOfPages;
				reorganizeButtonsPageNavigator(previousPage);
				runChangePageEvent();
			}
		}
		else if (buttonPressed == buttonPrevious)
		{ // Management of button previous
			previousPage = currentPageOfResults;
			if (currentPageOfResults != 1)
			{
				currentPageOfResults--;
			}
			else
			{
				currentPageOfResults = numberTotalOfPages;
			}
			reorganizeButtonsPageNavigator(previousPage);
			runChangePageEvent();
			
		}
		else if (buttonPressed == buttonNext)
		{ // Management of button back
			previousPage = currentPageOfResults;
			if (currentPageOfResults < numberTotalOfPages)
			{
				currentPageOfResults++;
			}
			else
			{
				currentPageOfResults = 1;
			}
			reorganizeButtonsPageNavigator(previousPage);
			runChangePageEvent();
		}
		else
		{// Management of buttons page
			ButtonPageNavigator button = (ButtonPageNavigator) buttonPressed;
			if (currentPageOfResults != button.getPage())
			{
				previousPage = currentPageOfResults;
				currentPageOfResults = button.getPage();
				reorganizeButtonsPageNavigator(previousPage);
				runChangePageEvent();
			}
		}
	}
	
	/**
	 * Reorganizes the caption of buttons each time we change a page.
	 */
	private void reorganizeButtonsPageNavigator(int previousPage)
	{
		if (numberTotalOfPages > numberOfButtonsPage)
		{ // if we have more pages of results then the number of buttons -> we
			// need to reorganize every button
			if (currentPageOfResults <= buttonPageMargin)
			{ // current page is in the lower margin
				for (int i = 0; i < numberOfButtonsPage; i++)
				{
					listButtons.get(i).setCaptionCkeckActualPage(i + 1);
				}
			}
			else if (currentPageOfResults >= numberTotalOfPages
					- buttonPageMargin)
			{ // current page is in the higher margin
				for (int i = numberTotalOfPages, j = numberOfButtonsPage - 1; j >= 0; i--, j--)
				{
					listButtons.get(j).setCaptionCkeckActualPage(i);
				}
			}
			else
			{
				for (int i = currentPageOfResults - buttonPageMargin, j = 0; i < currentPageOfResults
						+ buttonPageMargin; i++, j++)
				{ // // current page is between in the higher margin
					listButtons.get(j).setCaptionCkeckActualPage(i);
				}
			}
		}
		else
		{ // if we have less pages of results then the number of buttons -> we
			// only need to set bold the button of the current page and remove
			// bold at the button of the previous page
			listButtons.get(previousPage - 1).setCaptionNormal();
			listButtons.get(currentPageOfResults - 1).setCaptionCurrent();
		}
	}
	
	private void runChangePageEvent()
	{
		fireEvent(new ChangePageEvent(this, getPageRange()));
	}
	
	private void calculateButtonPageMargin()
	{
		buttonPageMargin = (int) Math.ceil(numberOfButtonsPage / 2);
	}
	
	private void createButtonsPage()
	{
		if (!listButtons.isEmpty())
		{
			listButtons.clear();
			buttonsPageLayout.removeAllComponents();
		}
		for (int i = 0; i < numberTotalOfPages && i < numberOfButtonsPage; i++)
		{
			ButtonPageNavigator buttonPage = new ButtonPageNavigator(i + 1,
					this);
			listButtons.add(buttonPage);
			buttonsPageLayout.addComponent(buttonPage);
		}
	}
	
	private void refreshStyleOfButtonsPage()
	{
		for (ButtonPageNavigator button : listButtons)
		{
			button.CheckActualPageAndSetStyle();
		}
	}
	
	public void changePage(int page)
	{
		if (page == currentPageOfResults)
		{
			return;
		}
		
		int previousPage = currentPageOfResults;
		currentPageOfResults = page;
		reorganizeButtonsPageNavigator(previousPage);
		runChangePageEvent();
	}
	
	public int getCurrentPage()
	{
		return currentPageOfResults; 
	}
	
	public void addListener(PagingComponentListener listener)
	{
		addListener(ChangePageEvent.class, listener, METHOD_DISPLAY_PAGE);
	}
	
	public void removeListener(PagingComponentListener listener)
	{
		removeListener(ChangePageEvent.class, listener, METHOD_DISPLAY_PAGE);
	}
	
	public void setNumberOfButtonsPage(int numberOfButtonsPage)
	{
		if (numberOfButtonsPage % 2 == 1)
		{ // check if the parameter is a odd number
			this.numberOfButtonsPage = numberOfButtonsPage;
			calculateButtonPageMargin();
			createButtonsPage();
			reorganizeButtonsPageNavigator(currentPageOfResults);
		}
		else
		{
			throw new RuntimeException(
					"Exception in PagingComponant: The number of buttons Page must be odd (ex: 9, 11, 23, ...) else the algorithme will be broken. You have set this number at "
							+ numberOfButtonsPage);
		}
	}
	
	public PageRange getPageRange()
	{
		return new PageRange(currentPageOfResults, hitsPerPage, numberOfResults);
	}
	
	public List<ButtonPageNavigator> getButtonsPage()
	{
		return listButtons;
	}
	
	public Button getButtonPrevious()
	{
		return buttonPrevious;
	}
	
	public Button getButtonNext()
	{
		return buttonNext;
	}
	
	public Button getButtonFirst()
	{
		return buttonFirst;
	}
	
	public Button getButtonLast()
	{
		return ButtonLast;
	}
	
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	// ///////////////////////// //**these methods are used to control the style
	// of buttons**
	
	/**
	 * Sets the same style to all buttons. First, Previous, 1, 2 ... Next, Last
	 */
	public void setStyleNameForAllButtons(String style)
	{
		if (style == null || "".equals(style))
		{
			return;
		}
		setStyleNameButtonsPreviousAndNext(style);
		setStyleNameButtonsFirstAndLast(style);
		stylesButtonNormal.clear();
		stylesButtonCurrent.clear();
		stylesButtonNormal.add(style);
		stylesButtonCurrent.add(style);
		refreshStyleOfButtonsPage();
	}
	
	/**
	 * Sets the style to buttons Previous and Next
	 */
	public void setStyleNameButtonsPreviousAndNext(String style)
	{
		if (style == null || "".equals(style))
		{
			return;
		}
		if (buttonPrevious == null || buttonNext == null)
		{
			return;
		}
		buttonPrevious.setStyleName(style);
		buttonNext.setStyleName(style);
	}
	
	/**
	 * Sets the style to buttons First and Last
	 */
	public void setStyleNameButtonsFirstAndLast(String style)
	{
		if (style == null || "".equals(style))
		{
			return;
		}
		if (buttonFirst == null || ButtonLast == null)
		{
			return;
		}
		buttonFirst.setStyleName(style);
		ButtonLast.setStyleName(style);
	}
	
	/**
	 * Sets the style to a button of current page
	 */
	public void setStyleNameCurrentButtonState(String style)
	{
		if (style == null || "".equals(style))
		{
			return;
		}
		stylesButtonCurrent.clear();
		stylesButtonCurrent.add(style);
		refreshStyleOfButtonsPage();
	}
	
	/**
	 * Sets the style to the buttons that don't correspond to the current page
	 */
	public void setStyleNameNormalButtonsState(String style)
	{
		if (style == null || "".equals(style))
		{
			return;
		}
		stylesButtonNormal.clear();
		stylesButtonNormal.add(style);
		refreshStyleOfButtonsPage();
	}
	
	/**
	 * Adds an existing style to all buttons
	 */
	public void addStyleNameForAllButtons(String style)
	{
		if (style == null || "".equals(style))
		{
			return;
		}
		addStyleNameButtonsPreviousAndNext(style);
		addStyleNameButtonsFirstAndLast(style);
		stylesButtonNormal.add(style);
		stylesButtonCurrent.add(style);
		refreshStyleOfButtonsPage();
	}
	
	/**
	 * Adds an existing style to buttons Previous and Next
	 */
	public void addStyleNameButtonsPreviousAndNext(String style)
	{
		if (style == null || "".equals(style))
		{
			return;
		}
		if (buttonPrevious == null || buttonNext == null)
		{
			return;
		}
		buttonPrevious.addStyleName(style);
		buttonNext.addStyleName(style);
	}
	
	/**
	 * Adds an existing style to buttons First and Last
	 */
	public void addStyleNameButtonsFirstAndLast(String style)
	{
		if (style == null || "".equals(style))
		{
			return;
		}
		if (buttonFirst == null || ButtonLast == null)
		{
			return;
		}
		buttonFirst.addStyleName(style);
		ButtonLast.addStyleName(style);
	}
	
	/**
	 * Adds an existing style to a button of current page
	 */
	public void addStyleNameCurrentButtonState(String style)
	{
		if (style == null || "".equals(style))
		{
			return;
		}
		stylesButtonCurrent.add(style);
		refreshStyleOfButtonsPage();
	}
	
	/**
	 * Adds an existing style to the buttons that don't correspond to the
	 * current page
	 */
	public void addStyleNameNormalButtonsState(String style)
	{
		if (style == null || "".equals(style))
		{
			return;
		}
		stylesButtonNormal.add(style);
		refreshStyleOfButtonsPage();
	}
	
	// //////////////////////////////////////////// INNER CLASS
	// /////////////////////////////////////
	// //////////////////////////////////////////// INNER CLASS
	// /////////////////////////////////////
	// //////////////////////////////////////////// INNER CLASS
	// /////////////////////////////////////
	// //////////////////////////////////////////// INNER CLASS
	// /////////////////////////////////////
	// //////////////////////////////////////////// INNER CLASS
	// /////////////////////////////////////
	
	/**
	 * Button link used for buttons around the page buttons.
	 */
	private static class ButtonNavigator extends Button
	{
		
		public ButtonNavigator()
		{
			setImmediate(true);
		}
		
		public ButtonNavigator(String caption, ClickListener listener)
		{
			super(caption, listener);
			setImmediate(true);
		}
	}
	
	/**
	 * Button link used for buttons of pages
	 */
	@SuppressWarnings("unchecked")
	private class ButtonPageNavigator extends ButtonNavigator
	{
		private int page;
		
		public ButtonPageNavigator(int page)
		{
			setCaptionCkeckActualPage(page);
		}
		
		public ButtonPageNavigator(int page, ClickListener listener)
		{
			this(page);
			addListener(listener);
		}
		
		public void setCaptionCkeckActualPage(int page)
		{
			setPage(page);
			CheckActualPageAndSetStyle();
		}
		
		public void CheckActualPageAndSetStyle()
		{
			if (page == currentPageOfResults)
			{
				setCaptionCurrent();
			}
			else
			{
				setCaptionNormal();
			}
		}
		
		public void setCaptionCurrent()
		{
			setStylesName(stylesButtonCurrent);
			focus();
		}
		
		public void setCaptionNormal()
		{
			setStylesName(stylesButtonNormal);
		}
		
		public void setPage(int page)
		{
			this.page = page;
			setCaption(String.valueOf(page));
		}
		
		public int getPage()
		{
			return page;
		}
		
		private void setStylesName(List<String> styles)
		{
			if (!styles.isEmpty())
			{
				setStyleName(styles.get(0));
				for (int i = 1; i < styles.size(); i++)
				{
					addStyleName(styles.get(i));
				}
			}
		}
	}
	
	/**
	 * Calculates the list of items which will be displayed.
	 */
	public static class PageRange
	{
		private int indexPageStart, indexPageEnd, hitsPerPage;
//		private List<I> itemsList;
		
		public PageRange(int currentPageOfResults, int hitsPerPage, int total)
		{
			this.hitsPerPage = hitsPerPage;
			indexPageStart = (currentPageOfResults - 1) * hitsPerPage;
			indexPageEnd = currentPageOfResults * hitsPerPage;
			if (indexPageEnd > total)
			{
				indexPageEnd = total;
			}
//			this.itemsList = itemsList.subList(indexPageStart, indexPageEnd);
		}
//		
//		public PageRange(int currentPageOfResults, int hitsPerPage,
//				List<I> itemsList)
//		{
//			indexPageStart = (currentPageOfResults - 1) * hitsPerPage;
//			indexPageEnd = currentPageOfResults * hitsPerPage;
//			if (indexPageEnd >= itemsList.size())
//			{
//				indexPageEnd = itemsList.size();
//			}
//			this.itemsList = itemsList.subList(indexPageStart, indexPageEnd);
//		}
		
		/**
		 * Gets items which will be displayed (in the current page)
		 */
//		public List<I> getItemsList()
//		{
//			return itemsList;
//		}
		
		// **this two fowling methods can be useful when working with DAO
		// Example : PagingComponent only holds primary keys and for current
		// page an query is executed with a range of indexPageStart and
		// indexPageEnd
		
		public int getIndexPageStart()
		{
			return indexPageStart;
		}
		
		public int getNumberOfRowsPerPage()
		{
			return hitsPerPage;
		}
		
		public int getIndexPageEnd()
		{
			return indexPageEnd;
		}
	}
	
	/** Listens when changing a page */
	public static interface PagingComponentListener extends Serializable
	{
		public void displayPage(ChangePageEvent event);
	}
	
	/** It's fired when changing a page */
	public static class ChangePageEvent extends Event
	{
		private PageRange pageRange;
		
		public ChangePageEvent(Component source, PageRange pageRange)
		{
			super(source);
			this.pageRange = pageRange;
		}
		
		public PageRange getPageRange()
		{
			return pageRange;
		}
	}
}