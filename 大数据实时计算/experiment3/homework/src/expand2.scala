import java.util.{Properties, UUID}
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala.DataSet
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010

import scala.reflect.internal.util.NoFile.input

object expand2 {
  /**
   * 输入的主题名称
   */
  val inputTopic = "zzy_buy_ticket_1"
  /**
   * kafka地址
   */
  val bootstrapServers = "bigdata35.depts.bingosoft.net:29035,bigdata36.depts.bingosoft.net:29036,bigdata37.depts.bingosoft.net:29037"

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val kafkaProperties = new Properties()
    kafkaProperties.put("bootstrap.servers", bootstrapServers)
    kafkaProperties.put("group.id", UUID.randomUUID().toString)
    kafkaProperties.put("auto.offset.reset", "earliest")
    kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val kafkaConsumer = new FlinkKafkaConsumer010[String](inputTopic,
      new SimpleStringSchema, kafkaProperties)
    kafkaConsumer.setCommitOffsetsOnCheckpoints(true)
    val inputKafkaStream = env.addSource(kafkaConsumer)
    val text = inputKafkaStream.map{x=>("0",1,x)}
      .map(x=>x.toString())
      .flatMap{str=>str.split(",")}
      .filter{_.contains("destination")}
      .map{
        (_,1)
      }
    val datastream = text
      .keyBy(0).sum(1)
    val dstream = datastream
      .keyBy(0).maxBy(1)
    dstream.map(x=>println(x))
    env.execute()
  }
}