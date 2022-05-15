package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.asteroidsplittingsystem.AsteroidSplitter;
import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidControlSystem;
import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;
import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
import dk.sdu.mmmi.cbse.collisiondetectionsystem.CollisionDetection;
import dk.sdu.mmmi.cbse.common.IShapeRender;
import dk.sdu.mmmi.cbse.common.MyShapeRender;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
import dk.sdu.mmmi.cbse.lifeprocessersystem.LifeProcesser;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collection;

public class Game implements ApplicationListener {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final ApplicationContext appContext = new ClassPathXmlApplicationContext("Asteroid.xml", "AsteroidSplitter.xml", "Enemy.xml", "Player.xml", "LifeProcesser.xml", "CollisionDetection.xml", "Bullet.xml");
    private Collection<? extends IPostEntityProcessingService> postEntityProcessingServices;
    private Collection<? extends IEntityProcessingService> entityProcessingServices;
    private IShapeRender sr;

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        OrthographicCamera cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2f, gameData.getDisplayHeight() / 2f);
        cam.update();

        sr = new MyShapeRender(new ShapeRenderer());

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        entityProcessingServices = getEntityProcessingServices();
        postEntityProcessingServices = getPostEntityProcessingServices();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        for (IEntityProcessingService entityProcessorService : entityProcessingServices) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessingServices) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            entity.draw(sr);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        Collection<IGamePluginService> plugins = new ArrayList<>();
        plugins.add(appContext.getBean("AsteroidPlugin", AsteroidPlugin.class));
        plugins.add(appContext.getBean("EnemyPlugin", EnemyPlugin.class));
        plugins.add(appContext.getBean("PlayerPlugin", PlayerPlugin.class));
        plugins.add(appContext.getBean("BulletPlugin", BulletPlugin.class));
        return plugins;
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        Collection<IEntityProcessingService> services = new ArrayList<>();
        services.add(appContext.getBean("AsteroidControlSystem", AsteroidControlSystem.class));
        services.add(appContext.getBean("EnemyControlSystem", EnemyControlSystem.class));
        services.add(appContext.getBean("PlayerControlSystem", PlayerControlSystem.class));
        services.add(appContext.getBean("BulletControlSystem", BulletControlSystem.class));
        return services;
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        Collection<IPostEntityProcessingService> services = new ArrayList<>();
        services.add(appContext.getBean("AsteroidSplitter", AsteroidSplitter.class));
        services.add(appContext.getBean("CollisionDetection", CollisionDetection.class));
        services.add(appContext.getBean("LifeProcesser", LifeProcesser.class));
        return services;
    }
}
