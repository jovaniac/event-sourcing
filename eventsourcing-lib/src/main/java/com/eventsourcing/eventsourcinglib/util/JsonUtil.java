/**
 * 
 */
package com.eventsourcing.eventsourcinglib.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author kaihe
 *
 */
public class JsonUtil {
  JsonUtil() {

  }

  private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

  private static ObjectMapper createObjectMapper() {

    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
    // objectMapper.registerModule(new
    // Hibernate4Module().enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING));
    objectMapper.registerModule(new JavaTimeModule());

    return objectMapper;
  }

  public static String object2Json(Object o) {
    StringWriter sw = new StringWriter();
    JsonGenerator gen = null;
    try {
      gen = new JsonFactory().createGenerator(sw);
      OBJECT_MAPPER.writeValue(gen, o);
    } catch (IOException e) {
      throw new RuntimeException("error with object to Json", e);
    } finally {
      if (null != gen) {
        try {
          gen.close();
        } catch (IOException e) {
          throw new RuntimeException("error with object to Json:", e);
        }
      }
    }
    return sw.toString();
  }

  public static Map<String, String> object2Map(Object o) {
    return OBJECT_MAPPER.convertValue(o, Map.class);
  }

  public static <T> T json2Object(String json, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(json, clazz);
    } catch (IOException e) {
      throw new RuntimeException("error with Json to Object:" + json, e);
    }
  }

  public static <T> List<T> json2List(String json, Class<T> clazz) throws IOException {
    JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);

    List<T> list = OBJECT_MAPPER.readValue(json, type);
    return list;
  }

  public static <T> T[] json2Array(String json, Class<T[]> clazz) throws IOException {
    return OBJECT_MAPPER.readValue(json, clazz);

  }

  public static JsonNode object2Node(Object o) {
    try {
      if (o == null) {
        return OBJECT_MAPPER.createObjectNode();
      } else {
        return OBJECT_MAPPER.convertValue(o, JsonNode.class);
      }
    } catch (Exception e) {
      throw new RuntimeException("error with object to Json", e);
    }
  }

  public static String getRequestPayload(HttpMethod httpMethod, String url, Object request) {
    JsonNode requestObject = OBJECT_MAPPER.createObjectNode();
    ((ObjectNode) requestObject).put(CommonConstants.REQUEST_PAYLOAD_URI, url);
    ((ObjectNode) requestObject).put(CommonConstants.REQUEST_PAYLOAD_METHOD, httpMethod.toString());
    JsonNode requestJsonNode = JsonUtil.object2Node(request);
    ((ObjectNode) requestObject).set(CommonConstants.REQUEST_PAYLOAD_BODY, requestJsonNode);

    return requestObject.toString();
  }
}
