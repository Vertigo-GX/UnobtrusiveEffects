package vertigo.unobtrusiveeffects.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vertigo.unobtrusiveeffects.UnobtrusiveEffectsClient;

@Mixin(SingleQuadParticle.class)
public abstract class SingleQuadParticleMixin extends Particle {

	protected SingleQuadParticleMixin(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z);
	}

	@Inject(method = "extract", at = @At("HEAD"), cancellable = true)
	public void extractInject(QuadParticleRenderState state, Camera camera, float time, CallbackInfo info) {
		if(UnobtrusiveEffectsClient.CONFIG.swirlCullingDistance != 0 && !camera.isDetached() && camera.position().distanceToSqr(this.x, this.y, this.z) < UnobtrusiveEffectsClient.CONFIG.swirlCullingDistance) {
			info.cancel();
		}
	}

}