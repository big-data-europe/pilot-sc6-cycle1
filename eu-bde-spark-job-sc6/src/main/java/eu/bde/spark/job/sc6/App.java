package eu.bde.spark.job.sc6;

import eu.bde.sc6.budget.parser.api.TransformationException;
import eu.bde.sc6.budget.parser.api.UnknownBudgetDataParserException;
import eu.bde.sc6.budget.parser.impl.BudgetDataParserRegistryImpl;
import eu.bde.virtuoso.utils.VirtuosoInserter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kafka.serializer.DefaultDecoder;
import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;
import org.apache.spark.storage.StorageLevel;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.rio.RDFHandlerException;
import org.slf4j.LoggerFactory;

public class App {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(App.class);

    //./spark-submit --class ${SPARK_APPLICATION_MAIN_CLASS} --master ${SPARK_MASTER_URL} ${SPARK_APPLICATION_JAR_LOCATION}
    public static void main(String[] args) {
        // e.g. kafka-sandbox
        String APP_NAME = System.getenv("APP_NAME");
        // e.g.: /app/ , /home/turnguard/bin/apache/spark-1.6.0-bin-hadoop2.6
        String SPARK_HOME = System.getenv("SPARK_HOME");
        // e.g. eu.bde.spark.job.sc2.App
        String SPARK_APPLICATION_MAIN_CLASS = System.getenv("SPARK_APPLICATION_MAIN_CLASS");
        // e.g. spark://bigdata-one.semantic-web.at:8177
        String SPARK_MASTER_URL = System.getenv("SPARK_MASTER_URL");
        // e.g. bigdata-one.semantic-web.at:9092
        String KAFKA_METADATA_BROKER_LIST = System.getenv("KAFKA_METADATA_BROKER_LIST");
        // e.g. 192.168.88.219:2181/kafka
        String ZK_CONNECT = System.getenv("ZK_CONNECT");
        // e.g. sc2
        String KAFKA_GROUP_ID = System.getenv("KAFKA_GROUP_ID");
        // e.g. flume
        String KAFKA_TOPIC = System.getenv("KAFKA_TOPIC");
        // e.g. http://localhost:8890/sparql-graph-crud-auth
        String VIRTUOSO_HOST = System.getenv("VIRTUOSO_HOST");
        // e.g. dba
        String VIRTUOSO_USER = System.getenv("VIRTUOSO_USER");
        // e.g. dba
        String VIRTUOSO_PASS = System.getenv("VIRTUOSO_PASS");
        // e.g. urn:sc6
        String VIRTUOSO_DEFAULT_GRAPH = System.getenv("VIRTUOSO_DEFAULT_GRAPH");
        // e.g. 10000 (miliseconds)
        int DURATION = System.getenv("SPARK_DURATION")!=null?Integer.parseInt(System.getenv("SPARK_DURATION")):5000;
        
        SparkConf conf = new SparkConf()
                .setAppName(APP_NAME)
                .setSparkHome(SPARK_HOME)
                .setExecutorEnv("SPARK_APPLICATION_MAIN_CLASS", SPARK_APPLICATION_MAIN_CLASS)
                .setMaster(SPARK_MASTER_URL);

        JavaSparkContext sc = new JavaSparkContext(conf);
        
        
        JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(DURATION));
        

        Map<String, String> kafkaParams = new HashMap<>();
                            kafkaParams.put("metadata.broker.list", KAFKA_METADATA_BROKER_LIST);
                            kafkaParams.put("zk.connect", ZK_CONNECT);
                            kafkaParams.put("zookeeper.connect", ZK_CONNECT);
                            kafkaParams.put("group.id", KAFKA_GROUP_ID);
                            kafkaParams.put("max.partition.fetch.bytes", "42000000");
                            kafkaParams.put("fetch.message.max.bytes", "42000000");        
        Map<String, Integer> topicMap = new HashMap<>();
                             topicMap.put(KAFKA_TOPIC, 3);
        
                             
        JavaPairDStream<String, byte[]> input = KafkaUtils.createStream(ssc, String.class, byte[].class,
                StringDecoder.class, DefaultDecoder.class, kafkaParams, topicMap, StorageLevel.MEMORY_ONLY());
        
        input.foreachRDD((JavaPairRDD<String, byte[]> rdd) -> {
            /* usage example below: by flume pipeline definition rdd.key = fileName, rdd.value = file data as byte[] */            
            rdd.collect().parallelStream().forEach((Tuple2<String, byte[]> t) -> {                                 
                try {
                    String fileName = t._1 != null ? t._1 : new String(MessageDigest.getInstance("MD5").digest((new Date()).toString().getBytes()));
                    LOG.warn("parsing: " + fileName);                    
                    List<Statement> data = BudgetDataParserRegistryImpl.getInstance().getBudgetDataParserForFileName(fileName).transform(fileName, t._2);
                    
                    VirtuosoInserter inserter = new VirtuosoInserter(
                        new URL(VIRTUOSO_HOST),
                        new URIImpl(VIRTUOSO_DEFAULT_GRAPH),
                        VIRTUOSO_USER, 
                        VIRTUOSO_PASS
                    );
                    inserter.startRDF();
                    for(Statement s : data){
                        inserter.handleStatement(s);
                    }
                    inserter.endRDF();
                } catch ( RDFHandlerException | MalformedURLException | NoSuchAlgorithmException | UnknownBudgetDataParserException | TransformationException ex) {
                    LOG.warn(("problematic file: " + t._1), ex);
                }
            });
            return null;
        });
        ssc.start();
        ssc.awaitTermination();
    }
}
