
import javax.sql.DataSource
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.jdbc.DataSourceBuilder
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

private class KMySQLContainer(image: DockerImageName) : MySQLContainer<KMySQLContainer>(image)

class CleanDatabaseDataSourceExtension : BeforeEachCallback {

    override fun beforeEach(context: ExtensionContext?) {
        dataSource.connection.use {
            it.createStatement().execute("SET FOREIGN_KEY_CHECKS=0;")
            it.createStatement().execute("DELETE FROM microorganism")
            it.createStatement().execute("ALTER TABLE microorganism AUTO_INCREMENT = 1")
            it.createStatement().execute("DELETE FROM impeller")
            it.createStatement().execute("ALTER TABLE impeller AUTO_INCREMENT = 1")
            it.createStatement().execute("DELETE FROM cost_factor")
            it.createStatement().execute("ALTER TABLE cost_factor AUTO_INCREMENT = 1")
            it.createStatement().execute("DELETE FROM reactor")
            it.createStatement().execute("ALTER TABLE reactor AUTO_INCREMENT = 1")
            it.createStatement().execute("DELETE FROM simulation")
            it.createStatement().execute("ALTER TABLE simulation AUTO_INCREMENT = 1")
            it.createStatement().execute("DELETE FROM batch_cultivation")
            it.createStatement().execute("ALTER TABLE batch_cultivation AUTO_INCREMENT = 1")
            it.createStatement().execute("DELETE FROM centrifugation")
            it.createStatement().execute("ALTER TABLE centrifugation AUTO_INCREMENT = 1")
            it.createStatement().execute("DELETE FROM centrifuge")
            it.createStatement().execute("ALTER TABLE centrifuge AUTO_INCREMENT = 1")
            it.createStatement().execute("SET FOREIGN_KEY_CHECKS=1;")
        }
    }

    companion object {
        val dataSource = DatabaseContainer.datasource
    }
}

private object DatabaseContainer {

    private var mySQLContainer: KMySQLContainer = KMySQLContainer(
        DockerImageName.parse("mysql:8.0.35")

    ).apply {
        withUsername("springuser")
        withPassword("s56ntJp!?")
    }
    val datasource: DataSource
        get() = DataSourceBuilder
            .create()
            .url(mySQLContainer.jdbcUrl)
            .password(mySQLContainer.password)
            .username(mySQLContainer.username)
            .build()

    init {
        mySQLContainer.start()
    }
}