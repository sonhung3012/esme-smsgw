package org.vaadin.easyuploads;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import com.vaadin.data.Buffered;
import com.vaadin.data.Property;
import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;

/**
 * UploadField is a helper class that uses rather low level {@link Upload}
 * component in core Vaadin. Instead of implementing own {@link Receiver}
 * developer can just access the contents of the file or the {@link File} where
 * the contents was streamed to. In addition to easier access to the content,
 * UploadField also provides built in {@link ProgressIndicator} and displays
 * (partly) the uploaded file. What/how is displayed can be easily modified by
 * overriding {@link #updateDisplay()} method.
 * 
 * <p>
 * UploadField can also be used in a form to edit a property - that's where the
 * name Field comes from. Property can be of type (set with
 * {@link #setFieldType(FieldType)}, but also automatically based on property
 * data source) :
 * <ul>
 * <li>byte[] ({@link FieldType#BYTE_ARRAY}) (Example: image file saved as blob
 * in DB)
 * <li>String ({@link FieldType#UTF8_STRING}) (Example: field of type String in
 * a pojo, the text is in a text file on end users computer)
 * <li>File ({@link FieldType#FILE}) (Example: fied of type File in a pojo)
 * </ul>
 * <p>
 * Typical use cases: TODO
 * <p>
 * {@link #setStorageMode(StorageMode)} controls how upload file handles the
 * received data. {@link StorageMode#FILE} mode streams uploaded file contents
 * to a file (except possibly committed value to property), the
 * {@link StorageMode#MEMORY} mode keeps everything in memory. If the Field is
 * in {@link StorageMode#FILE} mode, the file creation can be controlled with
 * {@link #setFileFactory(FileFactory)}.
 * 
 * <p>
 * Limitations:
 * <ul>
 * <li>Read through modes are not supported properly.
 * <li> {@link StorageMode#FILE} does not support Buffered properly(?).
 * </ul>
 * 
 * @author Matti Tahvonen
 * 
 */
@SuppressWarnings({ "serial", "unused" })
public class UploadField extends CssLayout implements Field, StartedListener,
        FinishedListener, ProgressListener {
    private static final int MAX_SHOWN_BYTES = 5;

    private UploadFieldReceiver receiver;

    private Upload upload;
    private Label display = new Label("", Label.CONTENT_XHTML);

    private ProgressIndicator progress = new ProgressIndicator();

    private StorageMode storageMode;

    public UploadField() {
        this(StorageMode.FILE);
    }

    public UploadField(StorageMode mode) {
        setWidth("200px");
        setStorageMode(mode);
        upload = new Upload(null, receiver);
        upload.setImmediate(true);
        upload.addListener((StartedListener) this);
        upload.addListener((FinishedListener) this);
        upload.addListener((ProgressListener) this);
        upload.setButtonCaption("Choose File");
        progress.setVisible(false);
        progress.setPollingInterval(500);
        buildDefaulLayout();
    }

    public void setButtonCaption(String caption) {
        upload.setButtonCaption(caption);
    }

    public String getButtonCaption() {
        return upload.getButtonCaption();
    }

    public final CssLayout getRootLayout() {
        return this;
    }

    protected void buildDefaulLayout() {
        getRootLayout().removeAllComponents();
        getRootLayout().addComponent(upload);
        getRootLayout().addComponent(progress);
    }

    public void setStorageMode(StorageMode mode) {
        if (receiver == null || storageMode != mode) {
            switch (mode) {
            case MEMORY:
                if (getFieldType() == FieldType.FILE) {
                    throw new IllegalArgumentException(
                            "Storage mode cannot be memory if fields type is File!");
                }
                receiver = new MemoryBuffer();
                break;
            default:
                receiver = new FileBuffer() {
                    @Override
                    public FileFactory getFileFactory() {
                        return UploadField.this.getFileFactory();
                    }

                    @Override
                    public FieldType getFieldType() {
                        return UploadField.this.getFieldType();
                    };
                };
                break;
            }
            if (upload != null) {
                upload.setReceiver(receiver);
            }
            storageMode = mode;

        }
    }

    public enum StorageMode {
        MEMORY, FILE
    }

    /**
	 */
    public enum FieldType {
        UTF8_STRING, BYTE_ARRAY, FILE;

        public Class<?> getRawType() {
            switch (this) {
            case FILE:
                return File.class;
            case UTF8_STRING:
                return String.class;
            default:
                return Byte[].class;
            }
        }
    }

    private FieldType fieldType;

    private String lastFileName;

    public Class<?> getType() {
        return fieldType.getRawType();
    }

    public void setFieldType(FieldType type) {
        fieldType = type;
        if (type == FieldType.FILE) {
            setStorageMode(StorageMode.FILE);
        }
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public class MemoryBuffer implements UploadFieldReceiver {
        ByteArrayOutputStream outputBuffer = null;

        String mimeType;

        String fileName;

        public MemoryBuffer() {

        }

        /**
         * @see com.vaadin.ui.Upload.Receiver#receiveUpload(String, String)
         */
        public OutputStream receiveUpload(String filename, String MIMEType) {
            fileName = filename;
            mimeType = MIMEType;
            outputBuffer = new ByteArrayOutputStream();
            return outputBuffer;
        }

        public Object getValue() {
            if (outputBuffer == null) {
                return null;
            }
            byte[] byteArray = outputBuffer.toByteArray();
            if (getFieldType() == FieldType.BYTE_ARRAY) {
                return byteArray;
            } else {
                return new String(byteArray);
            }
        }

        public InputStream getContentAsStream() {
            byte[] byteArray = outputBuffer.toByteArray();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    byteArray);
            return byteArrayInputStream;
        }

        public void setValue(Object newValue) {
            mimeType = null;
            fileName = null;
            outputBuffer = new ByteArrayOutputStream();
            FieldType fieldType2 = getFieldType();
            switch (fieldType2) {
            case BYTE_ARRAY:
                byte[] newValueBytes = (byte[]) newValue;
                try {
                    outputBuffer.write(newValueBytes);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                break;
            case UTF8_STRING:
                try {
                    String newValueStr = (String) newValue;
                    outputBuffer.write(newValueStr.getBytes());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalStateException();
            }

        }

        public boolean isEmpty() {
            return outputBuffer == null || outputBuffer.size() == 0;
        }

        public long getLastFileSize() {
            return outputBuffer.size();
        }

        public String getLastMimeType() {
            return mimeType;
        }

        public String getLastFileName() {
            return fileName;
        }

    }

    private FileFactory fileFactory;

    private Button deleteButton;

    public void setFileFactory(FileFactory fileFactory) {
        this.fileFactory = fileFactory;
    }

    public FileFactory getFileFactory() {
        if (fileFactory == null) {
            fileFactory = new TempFileFactory();
        }
        return fileFactory;
    }

    public void setValue(Object newValue) {
        receiver.setValue(newValue);
        if (writeTroughMode) {
            commit();
        }
    }

    public Object getValue() {
        return receiver.getValue();
    }

    public InputStream getContentAsStream() {
        return receiver.getContentAsStream();
    }

    public void uploadStarted(StartedEvent event) {
        progress.setVisible(true);
        progress.setValue(0);
    }

    public void uploadFinished(FinishedEvent event) {
        progress.setVisible(false);
        lastFileName = event.getFilename();
        updateDisplay();
        fireValueChange();
    }

    protected void fireValueChange() {
        fireValueChange(true);
    }

    @Override
    public void requestRepaint() {
        super.requestRepaint();
    }

    protected void updateDisplay() {
//        if (!receiver.isEmpty()) {
//            display.setValue(getDisplayDetails());
//            if (display.getParent() == null) {
//                getRootLayout().addComponent(display);
//            }
//            addDeleteButton();
//        } else if (display.getParent() != null) {
//            buildDefaulLayout();
//        }
    }

    /**
     * Adds file delete button, if file deletes are allowed.
     */
    protected void addDeleteButton() {
        if (isFileDeletesAllowed()) {
            if (deleteButton == null) {
                deleteButton = new Button(getDeleteCaption());
                deleteButton.addListener(new Button.ClickListener() {
                    public void buttonClick(ClickEvent arg0) {
                        setValue(null);
                        getRootLayout().removeComponent(arg0.getButton());
                        updateDisplay();
                    }
                });
            }
            if (deleteButton.getApplication() == null) {
                attachDeleteButton(deleteButton);
            }
        }
    }

    protected void attachDeleteButton(Button b) {

        getRootLayout().addComponent(b);
    }

    protected String getDeleteCaption() {
        return "X";
    }

    /**
     * @return the string representing the file. The default implementation
     *         shows name, size and first characters of the file if in UTF8
     *         mode.
     */
    protected String getDisplayDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("File: ");
        sb.append(lastFileName);
        sb.append("</br> <em>");
        Object value = getValue();
        if (getFieldType() == FieldType.BYTE_ARRAY) {
            byte[] ba = (byte[]) value;
            int shownBytes = MAX_SHOWN_BYTES;
            if (ba.length < MAX_SHOWN_BYTES) {
                shownBytes = ba.length;
            }
            for (int i = 0; i < shownBytes; i++) {
                byte b = ba[i];
                sb.append(Integer.toHexString(b));
            }
            if (ba.length > MAX_SHOWN_BYTES) {
                sb.append("...");
                sb.append("(" + ba.length + " bytes)");
            }
        } else {
            String string = value == null ? null : value.toString();
            if (string.length() > 200) {
                string = string.substring(0, 199) + "...";
            }
            sb.append(string);
        }
        sb.append("</em>");
        return sb.toString();
    }

    public void updateProgress(long readBytes, long contentLength) {
        progress.setValue(readBytes / contentLength);
    }

    public void setFileDeletesAllowed(boolean fileDeletesAllowed) {
        this.fileDeletesAllowed = fileDeletesAllowed;
    }

    public boolean isFileDeletesAllowed() {
        return fileDeletesAllowed;
    }

    private boolean fileDeletesAllowed = true;

    // FIELD RELATED FIELDS

    /**
     * Connected data-source.
     */
    private Property dataSource = null;

    /**
     * The list of validators.
     */
    private LinkedList<Validator> validators = null;

    /**
     * Auto commit mode.
     */
    private boolean writeTroughMode = true;

    /**
     * Is the field modified but not committed.
     */
    private boolean modified = false;

    /**
     * Current source exception.
     */
    private Buffered.SourceException currentBufferedSourceException = null;

    /**
     * Are the invalid values allowed in fields ?
     */
    private boolean invalidAllowed = true;

    /**
     * Are the invalid values committed ?
     */
    private boolean invalidCommitted = false;

    /**
     * The tab order number of this field.
     */
    private int tabIndex = 0;

    /**
     * Required field.
     */
    private boolean required = false;

    /**
     * The error message for the exception that is thrown when the field is
     * required but empty.
     */
    private String requiredError = "";

    /**
     * Is automatic validation enabled.
     */
    private boolean validationVisible = true;

    private static final Method VALUE_CHANGE_METHOD;

    static {
        try {
            VALUE_CHANGE_METHOD = Property.ValueChangeListener.class
                    .getDeclaredMethod("valueChange",
                            new Class[] { Property.ValueChangeEvent.class });
        } catch (final java.lang.NoSuchMethodException e) {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error finding methods in AbstractField");
        }
    }

    /*
     * Adds a value change listener for the field. Don't add a JavaDoc comment
     * here, we use the default documentation from the implemented interface.
     */
    public void addListener(Property.ValueChangeListener listener) {
        addListener(AbstractField.ValueChangeEvent.class, listener,
                VALUE_CHANGE_METHOD);
    }

    /*
     * Removes a value change listener from the field. Don't add a JavaDoc
     * comment here, we use the default documentation from the implemented
     * interface.
     */
    public void removeListener(Property.ValueChangeListener listener) {
        removeListener(AbstractField.ValueChangeEvent.class, listener,
                VALUE_CHANGE_METHOD);
    }

    /**
     * Emits the value change event. The value contained in the field is
     * validated before the event is created.
     */
    protected void fireValueChange(boolean repaintIsNotNeeded) {
        fireEvent(new AbstractField.ValueChangeEvent(this));
        if (!repaintIsNotNeeded) {
            requestRepaint();
        }
    }

    /**
     * This method listens to data source value changes and passes the changes
     * forwards.
     * 
     * @param event
     *            the value change event telling the data source contents have
     *            changed.
     */
    public void valueChange(Property.ValueChangeEvent event) {
        // if (isReadThrough() || !isModified()) {
        // fireValueChange(false);
        // }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Component.Focusable#focus()
     */
    public void focus() {
        super.focus();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Component.Focusable#getTabIndex()
     */
    public int getTabIndex() {
        return tabIndex;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Component.Focusable#setTabIndex(int)
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    /**
     * Is this field required. Required fields must filled by the user.
     * 
     * If the field is required, it is visually indicated in the user interface.
     * Furthermore, setting field to be required implicitly adds "non-empty"
     * validator and thus isValid() == false or any isEmpty() fields. In those
     * cases validation errors are not painted as it is obvious that the user
     * must fill in the required fields.
     * 
     * On the other hand, for the non-required fields isValid() == true if the
     * field isEmpty() regardless of any attached validators.
     * 
     * 
     * @return <code>true</code> if the field is required .otherwise
     *         <code>false</code>.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the field required. Required fields must filled by the user.
     * 
     * If the field is required, it is visually indicated in the user interface.
     * Furthermore, setting field to be required implicitly adds "non-empty"
     * validator and thus isValid() == false or any isEmpty() fields. In those
     * cases validation errors are not painted as it is obvious that the user
     * must fill in the required fields.
     * 
     * On the other hand, for the non-required fields isValid() == true if the
     * field isEmpty() regardless of any attached validators.
     * 
     * @param required
     *            Is the field required.
     */
    public void setRequired(boolean required) {
        this.required = required;
        requestRepaint();
    }

    /**
     * Set the error that is show if this field is required, but empty. When
     * setting requiredMessage to be "" or null, no error pop-up or exclamation
     * mark is shown for a empty required field. This faults to "". Even in
     * those cases isValid() returns false for empty required fields.
     * 
     * @param requiredMessage
     *            Message to be shown when this field is required, but empty.
     */
    public void setRequiredError(String requiredMessage) {
        requiredError = requiredMessage;
        requestRepaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Field#getRequiredError()
     */
    public String getRequiredError() {
        return requiredError;
    }

    /**
     * Is the field empty?
     * 
     * In general, "empty" state is same as null..
     */
    protected boolean isEmpty() {
        return receiver.isEmpty();
    }

    /**
     * Is automatic, visible validation enabled?
     * 
     * If automatic validation is enabled, any validators connected to this
     * component are evaluated while painting the component and potential error
     * messages are sent to client. If the automatic validation is turned off,
     * isValid() and validate() methods still work, but one must show the
     * validation in their own code.
     * 
     * @return True, if automatic validation is enabled.
     */
    public boolean isValidationVisible() {
        return validationVisible;
    }

    /**
     * Enable or disable automatic, visible validation.
     * 
     * If automatic validation is enabled, any validators connected to this
     * component are evaluated while painting the component and potential error
     * messages are sent to client. If the automatic validation is turned off,
     * isValid() and validate() methods still work, but one must show the
     * validation in their own code.
     * 
     * @param validateAutomatically
     *            True, if automatic validation is enabled.
     */
    public void setValidationVisible(boolean validateAutomatically) {
        if (validationVisible != validateAutomatically) {
            requestRepaint();
            validationVisible = validateAutomatically;
        }
    }

    public void setCurrentBufferedSourceException(
            Buffered.SourceException currentBufferedSourceException) {
        this.currentBufferedSourceException = currentBufferedSourceException;
        requestRepaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.BufferedValidatable#isInvalidCommitted()
     */
    public boolean isInvalidCommitted() {
        return invalidCommitted;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.BufferedValidatable#setInvalidCommitted(boolean)
     */
    public void setInvalidCommitted(boolean isCommitted) {
        invalidCommitted = isCommitted;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Buffered#commit()
     */
    public void commit() throws Buffered.SourceException, InvalidValueException {
        if (dataSource != null && !dataSource.isReadOnly()) {
            if ((isInvalidCommitted() || isValid())) {
                final Object newValue = getValue();
                try {

                    // Commits the value to datasource.
                    dataSource.setValue(newValue);

                } catch (final Throwable e) {

                    // Sets the buffering state.
                    currentBufferedSourceException = new Buffered.SourceException(
                            this, e);
                    requestRepaint();

                    // Throws the source exception.
                    throw currentBufferedSourceException;
                }
            } else {
                /* An invalid value and we don't allow them, throw the exception */
                validate();
            }
        }

        boolean repaintNeeded = false;

        // The abstract field is not modified anymore
        if (modified) {
            modified = false;
            repaintNeeded = true;
        }

        // If successful, remove set the buffering state to be ok
        if (currentBufferedSourceException != null) {
            currentBufferedSourceException = null;
            repaintNeeded = true;
        }

        if (repaintNeeded) {
            requestRepaint();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Buffered#discard()
     */
    public void discard() throws Buffered.SourceException {
        if (dataSource != null) {
            Property dataSource2 = dataSource;
            setPropertyDataSource(null);
            setPropertyDataSource(dataSource2);
            modified = false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Buffered#isModified()
     */
    public boolean isModified() {
        return modified;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Buffered#isWriteThrough()
     */
    public boolean isWriteThrough() {
        return writeTroughMode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Buffered#setWriteThrough(boolean)
     */
    public void setWriteThrough(boolean writeTrough)
            throws Buffered.SourceException, InvalidValueException {
        if (writeTroughMode == writeTrough) {
            return;
        }
        writeTroughMode = writeTrough;
        if (writeTroughMode) {
            commit();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Buffered#isReadThrough()
     */
    public boolean isReadThrough() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Buffered#setReadThrough(boolean)
     */
    public void setReadThrough(boolean readTrough)
            throws Buffered.SourceException {
        // TODO implement readthrought somehow ?
        // throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Validatable#addValidator(com.vaadin.data.Validator)
     */
    public void addValidator(Validator validator) {
        if (validators == null) {
            validators = new LinkedList<Validator>();
        }
        validators.add(validator);
        requestRepaint();
    }

    /**
     * Gets the validators of the field.
     * 
     * @return the Unmodifiable collection that holds all validators for the
     *         field.
     */
    public Collection<Validator> getValidators() {
        if (validators == null || validators.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableCollection(validators);
    }

    /**
     * Removes the validator from the field.
     * 
     * @param validator
     *            the validator to remove.
     */
    public void removeValidator(Validator validator) {
        if (validators != null) {
            validators.remove(validator);
        }
        requestRepaint();
    }

    /**
     * Tests the current value against all registered validators.
     * 
     * @return <code>true</code> if all registered validators claim that the
     *         current value is valid, <code>false</code> otherwise.
     */
    public boolean isValid() {

        if (isEmpty()) {
            if (isRequired()) {
                return false;
            } else {
                return true;
            }
        }

        if (validators == null) {
            return true;
        }

        final Object value = getValue();
        for (final Iterator<Validator> i = validators.iterator(); i.hasNext();) {
            if (!(i.next()).isValid(value)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks the validity of the Validatable by validating the field with all
     * attached validators.
     * 
     * The "required" validation is a built-in validation feature. If the field
     * is required, but empty, validation will throw an EmptyValueException with
     * the error message set with setRequiredError().
     * 
     * @see com.vaadin.data.Validatable#validate()
     */
    public void validate() throws Validator.InvalidValueException {

        if (isEmpty()) {
            if (isRequired()) {
                throw new Validator.EmptyValueException(requiredError);
            } else {
                return;
            }
        }

        // If there is no validator, there can not be any errors
        if (validators == null) {
            return;
        }

        // Initialize temps
        Validator.InvalidValueException firstError = null;
        LinkedList<InvalidValueException> errors = null;
        final Object value = getValue();

        // Gets all the validation errors
        for (final Iterator<Validator> i = validators.iterator(); i.hasNext();) {
            try {
                (i.next()).validate(value);
            } catch (final Validator.InvalidValueException e) {
                if (firstError == null) {
                    firstError = e;
                } else {
                    if (errors == null) {
                        errors = new LinkedList<InvalidValueException>();
                        errors.add(firstError);
                    }
                    errors.add(e);
                }
            }
        }

        // If there were no error
        if (firstError == null) {
            return;
        }

        // If only one error occurred, throw it forwards
        if (errors == null) {
            throw firstError;
        }

        // Creates composite validator
        final Validator.InvalidValueException[] exceptions = new Validator.InvalidValueException[errors
                .size()];
        int index = 0;
        for (final Iterator<InvalidValueException> i = errors.iterator(); i
                .hasNext();) {
            exceptions[index++] = i.next();
        }

        throw new Validator.InvalidValueException(null, exceptions);
    }

    /**
     * Fields allow invalid values by default. In most cases this is wanted,
     * because the field otherwise visually forget the user input immediately.
     * 
     * @return true iff the invalid values are allowed.
     * @see com.vaadin.data.Validatable#isInvalidAllowed()
     */
    public boolean isInvalidAllowed() {
        return invalidAllowed;
    }

    /**
     * Fields allow invalid values by default. In most cases this is wanted,
     * because the field otherwise visually forget the user input immediately.
     * <p>
     * In common setting where the user wants to assure the correctness of the
     * datasource, but allow temporarily invalid contents in the field, the user
     * should add the validators to datasource, that should not allow invalid
     * values. The validators are automatically copied to the field when the
     * datasource is set.
     * </p>
     * 
     * @see com.vaadin.data.Validatable#setInvalidAllowed(boolean)
     */
    public void setInvalidAllowed(boolean invalidAllowed)
            throws UnsupportedOperationException {
        this.invalidAllowed = invalidAllowed;
    }

    /**
     * <p>
     * Sets the specified Property as the data source for the field. All
     * uncommitted changes to the field are discarded and the value is refreshed
     * from the new data source.
     * </p>
     * 
     * <p>
     * If the datasource has any validators, the same validators are added to
     * the field. Because the default behavior of the field is to allow invalid
     * values, but not to allow committing them, this only adds visual error
     * messages to fields and do not allow committing them as long as the value
     * is invalid. After the value is valid, the error message is not shown and
     * the commit can be done normally.
     * </p>
     * 
     * @param newDataSource
     *            the new data source Property.
     */
    public void setPropertyDataSource(Property newDataSource) {

        // Stops listening the old data source changes
        if (dataSource != null
                && Property.ValueChangeNotifier.class
                        .isAssignableFrom(dataSource.getClass())) {
            ((Property.ValueChangeNotifier) dataSource).removeListener(this);
        }

        Class<?> type = newDataSource == null ? String.class : newDataSource
                .getType();
        if (type == byte[].class) {
            setFieldType(FieldType.BYTE_ARRAY);
        } else if (type == File.class) {
            setFieldType(FieldType.FILE);
        } else if (type == String.class) {
            setFieldType(FieldType.UTF8_STRING);
        } else {
            throw new IllegalArgumentException("Property type " + type
                    + " is not compatible with UploadField");
        }

        // Sets the new data source
        dataSource = newDataSource;

        // Gets the value from source
        try {
            if (dataSource != null) {
                receiver.setValue(dataSource.getValue());
            }
            modified = false;
        } catch (final Throwable e) {
            currentBufferedSourceException = new Buffered.SourceException(this,
                    e);
            modified = true;
        }

        // Listens the new data source if possible
        if (dataSource instanceof Property.ValueChangeNotifier) {
            ((Property.ValueChangeNotifier) dataSource).addListener(this);
        }

        // Copy the validators from the data source
        if (dataSource instanceof Validatable) {
            final Collection<Validator> validators = ((Validatable) dataSource)
                    .getValidators();
            if (validators != null) {
                for (final Iterator<Validator> i = validators.iterator(); i
                        .hasNext();) {
                    addValidator(i.next());
                }
            }
        }

        updateDisplay();
    }

    public Property getPropertyDataSource() {
        return dataSource;
    }

    protected long getLastFileSize() {
        return receiver.getLastFileSize();
    }

    protected String getLastMimeType() {
        return receiver.getLastMimeType();
    }

    protected String getLastFileName() {
        return receiver.getLastFileName();
    }

}
