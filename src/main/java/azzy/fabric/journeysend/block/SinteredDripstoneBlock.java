package azzy.fabric.journeysend.block;

import azzy.fabric.journeysend.mixin.FallingBlockDestroyAccessor;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SinteredDripstoneBlock extends FallingBlock {

    private static final VoxelShape SHAPE_STRAIGHT = Block.createCuboidShape(5, 0, 5, 11, 12, 11);
    private static final VoxelShape SHAPE_INVERSE = Block.createCuboidShape(5, 4, 5, 11, 16, 11);

    public static final BooleanProperty INVERTED = BooleanProperty.of("inverted");

    public SinteredDripstoneBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(INVERTED, ctx.getWorld().getBlockState(ctx.getBlockPos().up()).isSideSolid(ctx.getWorld(), ctx.getBlockPos(), Direction.UP, SideShapeType.FULL));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
       if(!state.get(INVERTED) || canFallThrough(world.getBlockState(pos.up())))
           tryFall(state, world, pos, random);
    }

    public void tryFall(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY()) {
            FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, state);
            if(state.get(INVERTED)) {
                fallingBlockEntity.setHurtEntities(4F, 2000);
            }
            else {
                fallingBlockEntity.setHurtEntities(1F, 20);
            }
            ((FallingBlockDestroyAccessor) fallingBlockEntity).setDestroyedOnLanding(true);
            world.spawnEntity(fallingBlockEntity);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(INVERTED) ? SHAPE_INVERSE : SHAPE_STRAIGHT;
    }

    @Override
    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        BlockState blockState = world.getBlockState(pos);
        if (!blockState.get(INVERTED)) {
            entity.handleFallDamage(distance + 1.0F, 1.75F, DamageSource.STALAGMITE);
        } else {
            super.onLandedUpon(world, pos, entity, distance);
        }
    }

    @Override
    public DamageSource getDamageSource() {
        return DamageSource.FALLING_STALACTITE;
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        world.playSound(null, pos, SoundEvents.BLOCK_NETHERRACK_BREAK, SoundCategory.BLOCKS, 4F, 0.85F + world.getRandom().nextFloat() / 5F);
        world.setBlockState(pos, fallingBlockEntity.getBlockState());
        world.breakBlock(pos, false);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if(!world.isClient())
            tryFall(state, (ServerWorld) world, hit.getBlockPos(), world.getRandom());
    }

    @Override
    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtEntities(8F, 2000);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(INVERTED);
    }

    @Override
    public float getMaxModelOffset() {
        return 0.05F;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}
