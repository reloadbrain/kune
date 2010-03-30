package com.google.gwt.wave.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.wave.client.event.HasWaveLoadHandlers;
import com.google.gwt.wave.client.event.WaveLoadEvent;

final class WaveEmbed extends JavaScriptObject {

    /**
     * Creates a new WaveEmbed using the default Wave server URL:
     * <code>http://wave-devel.corp.google.com/a/google.com/</code>
     */
    public static native WaveEmbed newInstance() /*-{
        return new $wnd.WavePanel();
    }-*/;

    // Undocumented:
    // loadSearch
    // setContactProvider
    // provideContacts

    // Not yet available:
    // addParticipant
    // addReply

    /**
     * Creates a new WaveEmbed using the specified Wave server URL.
     * 
     * @param serverURL
     *            The URL of the Wave server to use
     */
    public static native WaveEmbed newInstance(String serverURL) /*-{
        // http://code.google.com/p/google-wave-resources/issues/detail?id=486
        var nwave = new $wnd.WavePanel(serverURL);
        var uiConfig = new $wnd.WavePanel.UIConfig();
        uiConfig.setFooterEnabled(true);
        uiConfig.setHeaderEnabled(true);
        uiConfig.setToolbarEnabled(true);
        nwave.setUIConfigObject(uiConfig);
        return nwave;
    }-*/;

    // Called from JSNI
    @SuppressWarnings("unused")
    private static void fireWaveLoadEvent(final HasWaveLoadHandlers source, final String waveId) {
        final UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
        final WaveLoadEvent event = new WaveLoadEvent(waveId);

        if (ueh != null) {
            try {
                source.fireEvent(event);
            } catch (final Exception ex) {
                ueh.onUncaughtException(ex);
            }
        } else {
            source.fireEvent(event);
        }
    }

    protected WaveEmbed() {
        // Required by JavaScriptObject types
    }

    /**
     * Adds the current user as a participant to the wave.
     * <p>
     * NOTE: Not yet functional in developer sandbox!
     */
    public native void addParticipant() /*-{
        this.addParticipant();
    }-*/;

    /**
     * Adds a reply to the currently loaded wave. An empty reply will be added
     * to the wave.
     * <p>
     * NOTE: Not yet functional in developer sandbox!
     */
    public native void addReply() /*-{
        this.addReply();
    }-*/;

    /**
     * Returns the ID of the created &lt;iframe>
     * 
     * @return the ID of the created &lt;iframe>
     */
    public native String getFrameId() /*-{
        return this.getFrameId();
    }-*/;

    /**
     * Actually creates the wave &gt;iframe> inside the given container. This is
     * not done in the constructor so the caller can set various initialization
     * options before the creation.
     * 
     * @param container
     *            The container element which will hold the Wave &gt;IFRAME>
     */
    public native void init(Element container) /*-{
        this.init(container);
    }-*/;

    /**
     * Loads the given wave into the wavePanel. This can be called before or
     * after init.
     * 
     * @param source
     *            An instance of HasWaveLoadHandlers where the WaveLoadEvent
     *            will be dispatched from
     * @param waveId
     *            The Id of the Wave to load
     */
    public native void loadWave(HasWaveLoadHandlers source, String waveId) /*-{
        this.loadWave(waveId, function() {
        @com.google.gwt.wave.client.WaveEmbed::fireWaveLoadEvent(Lcom/google/gwt/wave/client/event/HasWaveLoadHandlers;Ljava/lang/String;)(source, waveId);
        })
    }-*/;

    /**
     * Loads the given wave into the wavePanel. This can be called before or
     * after init.
     * 
     * @param waveId
     *            The Id of the Wave to load
     */
    public native void loadWave(String waveId) /*-{
        this.loadWave(waveId);
    }-*/;

    public native void setEditMode(boolean value) /*-{
        this.setEditMode(value);
    }-*/;

    public native void setToolbarVisible(boolean value) /*-{
        this.setToolbarVisible(value);
    }-*/;

    /**
     * Set the UI configuration for the wave. This must be done before init() is
     * called. Note that fontSize must be expressed in points, such as "12pts".
     * 
     * @param bgColor
     * @param color
     * @param font
     * @param fontSize
     */
    public native void setUIConfig(String bgColor, String color, String font, String fontSize) /*-{
        this.setUIConfig(bgColor, color, font, fontSize);
    }-*/;
}
