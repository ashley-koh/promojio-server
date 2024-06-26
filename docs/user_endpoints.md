# User Endpoints

Code found in `/controllers/UserController.java`

### Register New User [POST] `/user/register`

Creates a new user based on `username`, `password` and `name` (optional).

#### Parameters

| Name       | Type   | Section | Remarks  |
|------------|--------|---------|----------|
| `username` | String | _body_  | unique   |
| `password` | String | _body_  | hashed   |
| `name`     | String | _body_  | optional |


#### Example JSON Request

```json
{
    "name": "John Doe",
    "username": "johndoe",
    "password": "some_password_hash"
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "user": {
            "id": "660ba05d11dd4412779e5b35",
            "name": "John Doe",
            "username": "johndoe",
            "password": "some_password_hash",
            "profileImage": null,
            "points": 0,
            "tierPoints": 0,
            "memberTier": null,
            "promos": []
        }
    },
    "status": "success"
}
```

### User Login [POST] `/user/login`

Authenticate users based on their credentials. Returns their user object.

#### Parameters

| Name       | Type   | Section | Remarks |
|------------|--------|---------|---------|
| `username` | String | _body_  | _none_  |
| `password` | String | _body_  | _none_  |


#### Example JSON Request

```json
{
    "username": "johndoe",
    "password": "some_password_hash"
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "user": {
            "id": "660ba05d11dd4412779e5b35",
            "name": "John Doe",
            "username": "johndoe",
            "password": "some_password_hash",
            "profileImage": null,
            "points": 0,
            "tierPoints": 0,
            "memberTier": null,
            "promos": []
        }
    },
    "status": "success"
}
```

### Get User by ID [GET] `/user/{id}`

Get a user by their database id. Replace `{id}` with the id of the user when calling this endpoint.

#### Parameters

| Name | Type   | Section | Remarks |
|------|--------|---------|---------|
| `id` | String | _path_  | _none_  |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "user": {
            "id": "660ba05d11dd4412779e5b35",
            "name": "John Doe",
            "username": "johndoe",
            "password": "some_password_hash",
            "profileImage": null,
            "points": 0,
            "tierPoints": 0,
            "memberTier": null,
            "promos": []
        }
    },
    "status": "success"
}
```

### Get Leaderboard [GET] `/user/leaderboard`

Returns the top 10 users by tierPoints in order from highest to lowest.

#### Parameters

_None_

#### Example JSON Response (Success)
```json
"data": {
        "leaderboard": [
            {
                "id": "6614f1af66c3ff1fae776434",
                "name": "Ashley2",
                "username": "ashleykoh2",
                "password": "pass",
                "profileImage": null,
                "points": 0,
                "tierPoints": 5000,
                "memberTier": "platinum",
                "promos": []
            },
            {
                "id": "6614f1b666c3ff1fae776435",
                "name": "Ashley3",
                "username": "ashleykoh3",
                "password": "pass",
                "profileImage": null,
                "points": 0,
                "tierPoints": 2500,
                "memberTier": "gold",
                "promos": []
            },
            (...and the next 8 documents)
        ]
    },
    "status": "success"
}
```

### Update User Name [PATCH] `/user/{id}/update/details`

Updates the details of the user. Currently, that is only the `name` field. Remember to set the headers of the request.

#### Parameters

| Name       | Type   | Section  | Remarks |
|------------|--------|----------|--------|
| `id`       | String | _path_   | _none_ |
| `username` | String | _header_ | _none_  |
| `password` | String | _header_ | _none_  |
| `name`     | String | _body_   | _none_  |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35/update/details
```

#### Example JSON Request

```json
{
    "name": "Sam Saltman"
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "updated": true
    },
    "status": "success"
}
```

### Update User Points [PATCH] `/user/{id}/update/points`

Updates the `points` of the user, not to be confused with `tierPoints`. Remember to set the headers of the request.

#### Parameters

| Name       | Type    | Section  | Remarks        |
|------------|---------|----------|----------------|
| `id`       | String  | _path_   | _none_         |
| `username` | String  | _header_ | _none_         |
| `password` | String  | _header_ | _none_         |
| `points`   | Integer | _body_   | zero and above |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35/update/points
```

#### Example JSON Request

```json
{
    "points": 42
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "updated": true
    },
    "status": "success"
}
```

### Update User Tier Points [PATCH] `/user/{id}/update/tierpoints`

Updates the `tierPoints`. Remember to set the headers of the request.

#### Parameters

| Name         | Type    | Section  | Remarks        |
|--------------|---------|----------|----------------|
| `id`         | String  | _path_   | _none_         |
| `username`   | String  | _header_ | _none_         |
| `password`   | String  | _header_ | _none_         |
| `tierPoints` | Integer | _body_   | zero and above |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35/update/tierpoints
```

#### Example JSON Request

```json
{
    "tierPoints": 24
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "updated": true
    },
    "status": "success"
}
```

### Add Promo to User collection [POST] `/user/{id}/add/promo`

Attaches a promo to user based on the promo id.

#### Parameters

| Name       | Type    | Section  | Remarks        |
|------------|---------|----------|----------------|
| `id`       | String  | _path_   | _none_         |
| `username` | String  | _header_ | _none_         |
| `password` | String  | _header_ | _none_         |
| `promoId`  | String  | _body_   | _should exist_ |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35/add/promo
```

#### Example JSON Request

```json
{
    "promoId": "660ba422f8d36a3ba5b419ce"
}
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "updated": true
    },
    "status": "success"
}
```

### User Creates Promo [POST] `/user/{id}/create/promo`

User submitted Promo will be added to our database and will be attached to user document. Every promo the user creates, he/she will get 200 points.

#### Parameters

| Name               | Type    | Section  | Remarks        |
|--------------------|---------|----------|----------------|
| `id`               | String  | _path_   | _none_         |
| `username`         | String  | _header_ | _none_         |
| `password`         | String  | _header_ | _none_         |
| `brand`            | String  | _body_  | required                     |
| `category`         | String  | _body_  | required                     |
| `smallLabel`       | String  | _body_  | required                     |
| `bigLabel`         | String  | _body_  | required                     |
| `shortDescription` | String  | _body_  | required                     |
| `longDescription`  | String  | _body_  | required                     |
| `validity`         | String  | _body_  | required, format: YYYY-MM-DD |
| `points`           | Integer | _body_  | required                     |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35/create/promo
```

#### Example JSON Request

```json
{
  "brand": "nike",
  "category": "shop",
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
        "updated": true
    },
    "status": "success"
}
```
### Use Promo [POST] `/user/{user_id}/use/promo/{promo_id}`

Consumes a Promo that the use is in possession of and increments the users' points with the points from the promo.

#### Parameters

| Name       | Type    | Section  | Remarks      |
|------------|---------|----------|--------------|
| `user_id`  | String  | _path_   | should exist |
| `promo_id` | String  | _path_   | should exist |
| `username` | String  | _header_ | _none_       |
| `password` | String  | _header_ | _none_       |


#### Example Request URI

```
/user/6614f1a366c3ff1fae776432/use/promo/66150b533759f37bdd0d573a
```

#### Example JSON Response (Success)
```json
{
    "data": {
        "updated": true
    },
    "status": "success"
}
```

### Delete User [DELETE] `/user/{id}`

Delete a User based on their ID.

#### Parameters

| Name       | Type    | Section  | Remarks        |
|------------|---------|----------|----------------|
| `id`       | String  | _path_   | _none_         |


#### Example Request URI

```
/user/660ba05d11dd4412779e5b35
```

#### Example JSON Response (Success)
```json
{
    "data": null,
    "status": "success"
}
```
