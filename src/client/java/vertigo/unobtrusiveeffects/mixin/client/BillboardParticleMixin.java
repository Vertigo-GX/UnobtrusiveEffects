package vertigo.unobtrusiveeffects.mixin.client;

import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.BillboardParticleSubmittable;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vertigo.unobtrusiveeffects.UnobtrusiveEffectsClient;

@Mixin(BillboardParticle.class)
public abstract class BillboardParticleMixin extends Particle {

	protected BillboardParticleMixin(ClientWorld world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Inject(method = "render(Lnet/minecraft/client/particle/BillboardParticleSubmittable;Lnet/minecraft/client/render/Camera;F)V", at = @At("HEAD"),
			cancellable = true)
	public void renderInject(BillboardParticleSubmittable submittable, Camera camera, float tickProgress, CallbackInfo info) {
		if(UnobtrusiveEffectsClient.CONFIG.swirlCullingDistance != 0 && !camera.isThirdPerson() && camera.getCameraPos().squaredDistanceTo(this.x,
				this.y, this.z) < UnobtrusiveEffectsClient.CONFIG.swirlCullingDistance) {
			info.cancel();
		}
	}

}