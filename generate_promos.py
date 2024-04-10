import json
import random
import datetime

OUTPUT_FILE = "./src/main/resources/seed/promos.json"
NUM_OF_PROMOS = 100

BRANDS = {
    'Apple': 'https://www.apple.com/',
    'Google': 'https://www.google.com/',
    'Amazon': 'https://www.amazon.com/',
    'Microsoft': 'https://www.microsoft.com/',
    'Coca-Cola': 'https://www.coca-colacompany.com/',
    'Nike': 'https://www.nike.com/',
    'Disney': 'https://www.disney.com/',
    'Samsung': 'https://www.samsung.com/',
    'Toyota': 'https://www.toyota.com/',
    'Facebook': 'https://www.facebook.com/',
    'Walmart': 'https://www.walmart.com/',
    "McDonald's": 'https://www.mcdonalds.com/',
    'BMW': 'https://www.bmw.com/',
    'Mercedes-Benz': 'https://www.mercedes-benz.com/',
    'Adidas': 'https://www.adidas.com/',
    'Sony': 'https://www.sony.com/',
    'Intel': 'https://www.intel.com/',
    'Starbucks': 'https://www.starbucks.com/',
    'Ford': 'https://www.ford.com/',
    'Honda': 'https://www.honda.com/',
    'Pepsi': 'https://www.pepsi.com/',
    'Netflix': 'https://www.netflix.com/',
    'IKEA': 'https://www.ikea.com/',
    'Target': 'https://www.target.com/',
    'General Electric (GE)': 'https://www.ge.com/',
    'Porsche': 'https://www.porsche.com/',
    'IBM': 'https://www.ibm.com/',
    'Audi': 'https://www.audi.com/',
    'Chevrolet': 'https://www.chevrolet.com/',
    'Huawei': 'https://www.huawei.com/',
    'Verizon': 'https://www.verizon.com/',
    'LG': 'https://www.lg.com/',
    'Dell': 'https://www.dell.com/',
    'ExxonMobil': 'https://www.exxonmobil.com/',
    'Colgate': 'https://www.colgate.com/',
    'Gap': 'https://www.gap.com/',
    'Hyundai': 'https://www.hyundai.com/',
    'HP': 'https://www.hp.com/',
    'Nestlé': 'https://www.nestle.com/',
    'Visa': 'https://www.visa.com/',
    'Mastercard': 'https://www.mastercard.us/',
    'Chanel': 'https://www.chanel.com/',
    'Siemens': 'https://www.siemens.com/',
    'Panasonic': 'https://www.panasonic.com/',
    'Oracle': 'https://www.oracle.com/',
    'Johnson & Johnson': 'https://www.jnj.com/',
    'PepsiCo': 'https://www.pepsico.com/',
    '3M': 'https://www.3m.com/',
    "Kellogg's": 'https://www.kelloggs.com/',
    'Colgate-Palmolive': 'https://www.colgatepalmolive.com/',
    'American Express': 'https://www.americanexpress.com/',
    'eBay': 'https://www.ebay.com/',
    'Pfizer': 'https://www.pfizer.com/',
    'Subaru': 'https://www.subaru.com/',
    'Kia': 'https://www.kia.com/',
    'Ferrari': 'https://www.ferrari.com/',
    'UPS': 'https://www.ups.com/',
    'FedEx': 'https://www.fedex.com/',
    'KFC': 'https://www.kfc.com/',
    'Nissan': 'https://www.nissanusa.com/',
    'Shell': 'https://www.shell.com/',
    'Boeing': 'https://www.boeing.com/',
    'Tiffany & Co.': 'https://www.tiffany.com/',
    'Goldman Sachs': 'https://www.gs.com/',
    'Subway': 'https://www.subway.com/',
    'Hewlett Packard Enterprise (HPE)': 'https://www.hpe.com/',
    'Procter & Gamble': 'https://www.pg.com/',
    'General Motors': 'https://www.gm.com/',
    'Airbnb': 'https://www.airbnb.com/',
    'Cisco': 'https://www.cisco.com/',
    'Lenovo': 'https://www.lenovo.com/',
    "L'Oréal": 'https://www.loreal.com/',
    'General Mills': 'https://www.generalmills.com/',
    'Qualcomm': 'https://www.qualcomm.com/',
    'American Airlines': 'https://www.aa.com/',
    'Delta Air Lines': 'https://www.delta.com/',
    'Southwest Airlines': 'https://www.southwest.com/',
    'Emirates': 'https://www.emirates.com/',
    'Rolex': 'https://www.rolex.com/',
    'Louis Vuitton': 'https://www.louisvuitton.com/',
    'Cartier': 'https://www.cartier.com/',
    'Gucci': 'https://www.gucci.com/',
    'Prada': 'https://www.prada.com/',
    'Burberry': 'https://www.burberry.com/',
    'Zara': 'https://www.zara.com/',
    'H&M': 'https://www.hm.com/',
    'Calvin Klein': 'https://www.calvinklein.us/',
    'Ralph Lauren': 'https://www.ralphlauren.com/',
    'Tommy Hilfiger': 'https://www.tommy.com/'
}


SMALL_LABEL = [
    "Everything",
    "Storewide",
    "Sitewide",
    "All Orders",
    "Your Purchase",
    "Any Item",
    "Entire Purchase",
    "All Products",
    "Any Purchase",
    "Everything Online",
    "All Merchandise",
    "Any Order",
    "Entire Cart",
    "All Items",
    "Your Entire Order",
    "Everything in Store",
    "Your Entire Purchase",
    "All Products Online",
    "Everything Today",
    "Any Purchase",
    "Your Entire Cart",
    "All Orders Online",
    "Any Item in Store",
    "Everything Tomorrow",
    "All Products Today",
    "Any Order Online",
    "Entire Purchase Today",
    "Everything in Stock",
    "Any Item Tomorrow",
    "All Products Tomorrow",
    "Your Entire Purchase Today",
    "All Items Online",
    "Any Order Today",
    "Everything in Store Today",
    "Your Entire Cart Tomorrow",
    "All Orders Today",
    "Any Purchase Online",
    "Entire Purchase Tomorrow",
    "Everything Online Today",
    "All Products Tomorrow",
    "Any Item Today",
    "Everything Tomorrow",
    "Your Entire Cart Today",
    "All Orders Tomorrow",
    "Any Purchase Today",
    "Entire Purchase Tomorrow",
    "Everything Online Today",
    "All Products Tomorrow",
    "Your Entire Order Today",
    "Any Item Online"
]

SHORT_DESCRIPTION = "Lorem Ipsum"

LONG_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus faucibus orci leo, id lobortis risus blandit at. Aenean varius ac nisl vitae dapibus. Mauris viverra."

CATEGORIES = ["shop", "food", "travel", "others"]

def random_date(start, end):
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = random.randrange(int_delta)
    return start + datetime.timedelta(seconds=random_second)

def generate_promos():
    promos = []
    d1 = datetime.date.today() + datetime.timedelta(days=7)
    d2 = d1 + datetime.timedelta(weeks=55)

    for i in range(NUM_OF_PROMOS):
        promos.append({
            "brand": random.choice(list(BRANDS.keys())),
            "category": random.choice(CATEGORIES),
            "smallLabel": f"Off {random.choice(SMALL_LABEL)}",
            "bigLabel": f"{random.randint(10, 90)}%",
            "shortDescription": SHORT_DESCRIPTION,
            "longDescription": LONG_DESCRIPTION,
            "validity": random_date(d1, d2).strftime("%Y-%m-%d"),
            "points": random.randint(10, 500)
        })

    return promos


if __name__ == "__main__":
    promos = generate_promos()
    with open(OUTPUT_FILE, 'w') as f:
        json.dump(promos, f, ensure_ascii=False, indent=4)
