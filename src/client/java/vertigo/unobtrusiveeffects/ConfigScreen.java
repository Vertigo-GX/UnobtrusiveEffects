package vertigo.unobtrusiveeffects;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.text.DecimalFormat;

public class ConfigScreen extends Screen {

	private static final int BUTTON_WIDTH = 310;
	private static final int BUTTON_HEIGHT = 20;

	private final Screen parent;

	private boolean modified = false;

	protected ConfigScreen(Screen parent) {
		super(Text.literal("unobtrusive-effects.options"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
		layout.addHeader(new TextWidget(Text.translatable("unobtrusive-effects.text.optionsTitle"), this.textRenderer));
		GridWidget grid = new GridWidget();
		grid.setRowSpacing(5);
		GridWidget.Adder adder = grid.createAdder(1);
		adder.add(createToggleButton("disableFireOverlay", UnobtrusiveEffectsClient.CONFIG.disableFireOverlay, b -> {
			setToggleButtonMessage(b, "disableFireOverlay", UnobtrusiveEffectsClient.CONFIG.disableFireOverlay ^= true);
		}));
		adder.add(new FloatSlider("swirlCullingDistance", 10, UnobtrusiveEffectsClient.CONFIG.swirlCullingDistance, v -> {
			UnobtrusiveEffectsClient.CONFIG.swirlCullingDistance = v;
		}));
		layout.addBody(grid);
		layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, b -> {
			close();
		}).build());
		layout.forEachChild(this::addDrawableChild);
		layout.refreshPositions();
	}

	@Override
	public void close() {
		if (modified) {
			UnobtrusiveEffectsClient.CONFIG.write();
		}
		this.client.setScreen(this.parent);
	}

	private ButtonWidget createToggleButton(String key, boolean value, ButtonWidget.PressAction action) {
		return ButtonWidget.builder(ScreenTexts.composeToggleText(Text.translatable("unobtrusive-effects.option." + key), value), action).tooltip(Tooltip.of(Text.translatable("unobtrusive-effects.tooltip." + key))).size(BUTTON_WIDTH, BUTTON_HEIGHT).build();
	}

	private void setToggleButtonMessage(ButtonWidget button, String key, boolean value) {
		button.setMessage(ScreenTexts.composeToggleText(Text.translatable("unobtrusive-effects.option." + key), value));
		modified = true;
	}

	private class FloatSlider extends SliderWidget {

		private final DecimalFormat format = new DecimalFormat("0.0");
		private final String key;
		private final double max;
		private final FloatSetter setter;

		public FloatSlider(String key, int max, double value, FloatSetter setter) {
			super(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, Text.empty(), value / max);
			this.key = key;
			this.max = max;
			this.setter = setter;
			this.setTooltip(Tooltip.of(Text.translatable("unobtrusive-effects.tooltip." + key)));
			updateMessage();
		}

		@Override
		protected void updateMessage() {
			this.setMessage(ScreenTexts.composeGenericOptionText(Text.translatable("unobtrusive-effects.option." + key), value == 0f ? ScreenTexts.OFF : Text.of(format.format(max * value))));
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