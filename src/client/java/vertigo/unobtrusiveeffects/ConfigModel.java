package vertigo.unobtrusiveeffects;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;

@Config(name = "unobtrusive-effects", wrapperName = "ModConfig")
@Modmenu(modId = "unobtrusive-effects")
public class ConfigModel {

	public boolean hideFireOverlay = true;
	@RangeConstraint(min = 0.0f, max = 10f)
	public float swirlCullingDistance = 2.5f;

}