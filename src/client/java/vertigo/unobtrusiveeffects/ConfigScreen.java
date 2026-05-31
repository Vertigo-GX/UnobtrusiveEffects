package vertigo.unobtrusiveeffects;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ConfigScreen extends Screen {

	private static final int BUTTON_WIDTH = 310;

	private static final int BUTTON_HEIGHT = 20;

	private final Screen parent;

	private boolean modified = false;

	protected ConfigScreen(Screen parent) {
		super(Component.literal("unobtrusive-effects.options"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
		layout.addToHeader(new StringWidget(Component.translatable("unobtrusive-effects.text.optionsTitle"), this.font));
		GridLayout grid = new GridLayout();
		grid.rowSpacing(5);
		GridLayout.RowHelper helper = grid.createRowHelper(1);
		helper.addChild(createToggleButton("disableFireOverlay", UnobtrusiveEffectsClient.CONFIG.disableFireOverlay, b -> setToggleButtonMessage(b, "disableFireOverlay", UnobtrusiveEffectsClient.CONFIG.disableFireOverlay ^= true)));
		helper.addChild(new FloatSlider("swirlCullingDistance", 10, UnobtrusiveEffectsClient.CONFIG.swirlCullingDistance, v -> UnobtrusiveEffectsClient.CONFIG.swirlCullingDistance = v));
		layout.addToContents(grid);
		layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, b -> onClose()).build());
		layout.visitWidgets(this::addRenderableWidget);
		layout.arrangeElements();
	}

	@Override
	public void onClose() {
		if(modified) {
			UnobtrusiveEffectsClient.CONFIG.write();
		}
		this.minecraft.setScreen(this.parent);
	}

	private Button createToggleButton(String key, boolean value, Button.OnPress action) {
		return Button.builder(CommonComponents.optionStatus(Component.translatable("unobtrusive-effects.option." + key), value), action).tooltip(Tooltip.create(Component.translatable("unobtrusive-effects.tooltip." + key))).size(BUTTON_WIDTH, BUTTON_HEIGHT).build();
	}

	private void setToggleButtonMessage(Button button, String key, boolean value) {
		button.setMessage(CommonComponents.optionStatus(Component.translatable("unobtrusive-effects.option." + key), value));
		modified = true;
	}

	private class FloatSlider extends AbstractSliderButton {

		private final DecimalFormat format = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));

		private final String key;

		private final double max;

		private final FloatSetter setter;

		public FloatSlider(String key, int max, double value, FloatSetter setter) {
			super(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, Component.empty(), value / max);
			this.key = key;
			this.max = max;
			this.setter = setter;
			this.setTooltip(Tooltip.create(Component.translatable("unobtrusive-effects.tooltip." + key)));
			updateMessage();
		}

		@Override
		protected void updateMessage() {
			this.setMessage(CommonComponents.optionNameValue(Component.translatable("unobtrusive-effects.option." + key), value == 0f ? CommonComponents.OPTION_OFF : Component.nullToEmpty(format.format(max * value))));
		}

		@Override
		protected void applyValue() {
			setter.set(Float.parseFloat(format.format(max * value)));
			ConfigScreen.this.modified = true;
		}

	}

	private interface FloatSetter {

		void set(float value);

	}

}