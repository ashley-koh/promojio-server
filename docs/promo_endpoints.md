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
