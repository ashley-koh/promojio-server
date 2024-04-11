# Promo Endpoints

Code found in `/controllers/PromoController.java`

### Create New Promo [POST] `/promo`

Create a new Promo based on fields

#### Parameters

| Name               | Type    | Section | Remarks                      |
|--------------------|---------|---------|------------------------------|
| `brand`            | String  | _body_  | required                     |
| `smallLabel`       | String  | _body_  | required                     |
| `bigLabel`         | String  | _body_  | required                     |
| `shortDescription` | String  | _body_  | required                     |
| `longDescription`  | String  | _body_  | required                     |
| `validity`         | String  | _body_  | required, format: YYYY-MM-DD |
| `points`           | Integer | _body_  | required                     |


#### Example JSON Request

```json
{
  "brand": "nike",
  "smallLabel": "one for one",
  "bigLabel": "10",
  "shortDescription": "Brand New",
  "longDescription": "Longggggg Description",
  "validity": "2025-02-04",
  "points": 20
}

```

#### Example JSON Response (Success)
```json
{
    "data": {
        "promo": {
            "id": "661359554a0fcb17ed21f5c9",
            "brand": "nike",
            "logoImage": null,
            "smallLabel": "one for one",
            "bigLabel": "10",
            "shortDescription": "Brand New",
            "longDescription": "Longggggg Description",
            "validity": "2025-02-04T00:00:00.000+00:00",
            "points": 20
        }
    },
    "status": "success"
}
```

### Get All Promos [GET] `/promo`

Returns all unused promo codes. No JSON body. Just call the url.

#### Parameters

_None_

#### Example JSON Response (Success)
```json
{
  "data": {
    "promos": [
      {
        "id": "661359554a0fcb17ed21f5c9",
        "brand": "nike",
        "logoImage": null,
        "smallLabel": "one for one",
        "bigLabel": "10",
        "shortDescription": "Brand New",
        "longDescription": "Longggggg Description",
        "validity": "2025-02-04T00:00:00.000+00:00",
        "points": 20
      },
      {
        "id": "66135b0b4a0fcb17ed21f5ca",
        "brand": "adidas",
        "logoImage": null,
        "smallLabel": "get 1 free",
        "bigLabel": "buy three",
        "shortDescription": "Great Singapore Sale",
        "longDescription": "Longggggg Description",
        "validity": "2025-04-02T00:00:00.000+00:00",
        "points": 20
      }
    ]
  },
  "status": "success"
}
```

### Get Promo by ID [GET] `/promo/{id}`

Get a single promo by its database id.

#### Parameters

| Name | Type    | Section | Remarks                      |
|------|---------|---------|------------------------------|
| `id` | String  | _path_  | required                     |

#### Example Request URI

```
/promo/661359554a0fcb17ed21f5c9
```

#### Example JSON Response (Success)
```json
{
  "data": {
    "id": "661359554a0fcb17ed21f5c9",
    "brand": "nike",
    "logoImage": null,
    "smallLabel": "one for one",
    "bigLabel": "10",
    "shortDescription": "Brand New",
    "longDescription": "Longggggg Description",
    "validity": "2025-02-04T00:00:00.000+00:00",
    "points": 20
  },
  "status": "success"
}
```

### Get Random Promo [GET] `/promo/random`

Returns a single valid promo within a range of min and max points (both inclusive). If no promo exists within the range, return the next closest promo.

#### Parameters

| Name  | Type    | Section | Remarks                 |
|-------|---------|---------|-------------------------|
| `min` | Integer | _param_ | optional, defaults to 0 |
| `max` | Integer | _param_ | optional                |

#### Example Request URIs

Return any random promo from our entire database:
```
/promo/random
```

Return any random promo which is more than or equals to 20 points:
```
/promo/random?min=20
```

Return any random promo within a range of 21-50 points:
```
/promo/random?min=21&max=50
```

#### Example JSON Response (Success)
```json
{
  "data": {
    "promo": {
      "id": "66150bdc3759f37bdd0d5740",
      "brand": "nike",
      "logoImage": null,
      "smallLabel": "off",
      "bigLabel": "10",
      "shortDescription": "Free stuff",
      "longDescription": "Lalaland",
      "validity": "2025-02-04T00:00:00.000+00:00",
      "points": 25
    }
  },
  "status": "success"
}
```


### Delete Promo [DELETE] `/promo/{id}`

Delete a Promo based on their ID.

#### Parameters

| Name       | Type    | Section  | Remarks        |
|------------|---------|----------|----------------|
| `id`       | String  | _path_   | _none_         |


#### Example Request URI

```
/promo/661359554a0fcb17ed21f5c9
```

#### Example JSON Response (Success)
```json
{
    "data": null,
    "status": "success"
}
```
