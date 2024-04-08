# PromoJio Server

<div align="center">
  <div>
    <img src="https://img.shields.io/badge/-Docker-black?style=for-the-badge&logoColor=white&logo=docker&color=61DAFB" alt="docker" />
    <img src="https://img.shields.io/badge/-Spring Boot-black?style=for-the-badge&logoColor=white&logo=spring-boot&color=3CC10E" alt="spring boot" />
    <img src="https://img.shields.io/badge/-Mongo DB-black?style=for-the-badge&logoColor=white&logo=mongodb&color=91CCB3" alt="docker" />
  </div>

  <p align="center">Self-hosted cloud-based server for <a href=https://github.com/zayne-siew/PromoJio>PromoJio</a></p>
</div>

## Requirements

1. [IntelliJ Idea Community Edition](https://www.jetbrains.com/idea/download/?section=windows) - recommended (scroll down to black banner)\
Other IDEs (e.g. VSCode) also viable
2. [Docker](https://docs.docker.com/engine/install/) - follow the installation instructions from the official documentation

## Installation
1. Select `Get from Version Control` when creating new project.
2. Paste `https://github.com/ashley-koh/promojio-server.git` and select `Clone`
3. [If within IntelliJ] Right-click `compose.yml` and select `Run 'compose.yml': Compose...`\
[In Terminal] Run the following command:
```cmd
docker compose up --build
```
4. An instance of MongoDB and MongoExpress should now be running in the background
5. Build and run this git repo in IntelliJ Idea to run this server
6. Start Requesting!

## Usage

Use this function to get JSON from URL (not complete)
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

Above is adapted from [this StackOverflow answer](https://stackoverflow.com/questions/34691175/how-to-send-httprequest-and-get-json-response-in-android)