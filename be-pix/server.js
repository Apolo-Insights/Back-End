const express = require("express");
const cors = require("cors"); 
const { QrCodePix } = require("qrcode-pix");
const app = express();
const port = 7777;


app.use(cors());

app.get("/qrcode", async (req, res) => {
  const { value } = req.query;
  const qrCodePix = QrCodePix({
    version: "01",
    key: "11976900686",
    name: "Joao kevin Formiga",
    city: "SAO PAULO",
    message: "Pagamento PIX",
    cep: "05822010",
    value: parseFloat(value),
  });

  res.send(await qrCodePix.base64());
});

app.listen(port, () => {
  console.log(`SERVER RUNNING AT: http://localhost:${port}`);
});