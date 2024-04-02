# PromoJio Server  

---

## Requirements

1. [Intellij Idea Community Edition](https://www.jetbrains.com/idea/download/?section=windows) (Scroll Down to black banner)
2. [Docker](https://docs.docker.com/engine/install/)

## Installation
1. Select `Get from Version Control` when creating new project.
2. Paste `https://github.com/ashley-koh/promojio-server.git` and Select `Clone`
3. Right-click 'compose.yml' and select `Run 'compose.yml': Compose...`
4. An instance of MongoDB and MongoExpress should now be running in the background
5. Build and Run this git repo in Intellij Idea to run this server
6. Start Requesting!

## Usage

Use this function to get JSON from URL
```java
public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
    HttpURLConnection urlConnection = null;
    URL url = new URL(urlString);
    urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setRequestMethod("GET");
    urlConnection.setReadTimeout(10000 /* milliseconds */ );
    urlConnection.setConnectTimeout(15000 /* milliseconds */ );
    urlConnection.setDoOutput(true);
    urlConnection.connect();

    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
    StringBuilder sb = new StringBuilder();

    String line;
    while ((line = br.readLine()) != null) {
        sb.append(line + "\n");
    }
    br.close();

    String jsonString = sb.toString();
    System.out.println("JSON: " + jsonString);

    return new JSONObject(jsonString);
}
```

Do not forget to add Internet permission to your manifest

`<uses-permission android:name="android.permission.INTERNET" />`

Then use it like this

```java
try{
      JSONObject jsonObject = getJSONObjectFromURL(urlString);
      //
      // Parse your json here
      //
} catch (IOException e) {
      e.printStackTrace();
} catch (JSONException e) {
      e.printStackTrace();
}
```

Above is adapted from [this stackoverflow answer](https://stackoverflow.com/questions/34691175/how-to-send-httprequest-and-get-json-response-in-android)