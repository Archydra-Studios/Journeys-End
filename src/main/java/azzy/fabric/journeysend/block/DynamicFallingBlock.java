package azzy.fabric.journeysend.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class DynamicFallingBlock extends SandBlock {

    private final float chance, damage;
    private final boolean sensitive, spawnParticles, selfFalling;
    private final Supplier<DamageSource> source;

    public DynamicFallingBlock(int color, boolean spawnParticles, boolean sensitive, boolean selfFalling, float chance, float damage, Supplier<DamageSource> damageSource, Settings settings) {
        super(color, settings);
        this.spawnParticles = spawnParticles;
        this.damage = damage;
        this.source = damageSource;
        this.sensitive = sensitive;
        this.selfFalling = selfFalling;
        this.chance = chance;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        if(!world.isClient()) {
            if(damage > 0 && source != null) {
                entity.damage(source.get(), damage);
            }
            if(sensitive && world.getRandom().nextFloat() <= chance) {
                scheduledTick(world.getBlockState(pos), (ServerWorld) world, pos, world.getRandom());
            }
        }
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if(!world.isClient() && sensitive) {
            scheduledTick(state, (ServerWorld) world, hit.getBlockPos(), world.getRandom());
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(selfFalling && random.nextFloat() <= chance)
            scheduledTick(state, world, pos, random);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(spawnParticles) {
            super.randomDisplayTick(state, world, pos, random);
        }
    }
}
