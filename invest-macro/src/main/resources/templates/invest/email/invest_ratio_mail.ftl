<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>投资比例月报</title>
</head>
<body style="font-family:Arial,sans-serif;font-size:14px;line-height:1.8;color:#333;">
  <h2 style="margin-bottom:12px;">【投资比例月报】</h2>
  <div>推送时间：${bizTime}</div>
  <hr style="border:0;border-top:1px solid #eee;margin:12px 0;">

  <div style="margin-bottom:16px;">
    <p style="margin:0 0 10px 0;">尊敬的投资者：</p>
    <p style="margin:0 0 10px 0;">现将管老师给出的投资策略整理如下，供您参考：</p>
  </div>

  <h3 style="margin:18px 0 10px 0; font-size:16px;">核心资产配置比例</h3>
  <table style="width:100%;border-collapse:collapse;margin-bottom:16px;">
    <tr>
      <td style="padding:8px 10px;border:1px solid #eee;background:#fafafa;">货币类资产</td>
      <td style="padding:8px 10px;border:1px solid #eee;">${currencyRatio}%</td>
    </tr>
    <tr>
      <td style="padding:8px 10px;border:1px solid #eee;background:#fafafa;">债券类资产</td>
      <td style="padding:8px 10px;border:1px solid #eee;">${bondRatio}%</td>
    </tr>
    <tr>
      <td style="padding:8px 10px;border:1px solid #eee;background:#fafafa;">股票类资产</td>
      <td style="padding:8px 10px;border:1px solid #eee;">${stockRatio}%</td>
    </tr>
    <tr>
      <td style="padding:8px 10px;border:1px solid #eee;background:#fafafa;">商品类资产</td>
      <td style="padding:8px 10px;border:1px solid #eee;">${commodityRatio}%</td>
    </tr>
  </table>

  <div style="margin-top:6px;">
    <p style="margin:0 0 8px 0;"><strong>配置定位说明：</strong></p>
    <p style="margin:0 0 8px 0;">货币类资产作为核心配置，重点关注流动性与资金安全，为后续布局预留弹性空间；债券类资产承担稳健补充功能，有助于平衡收益与波动风险；股票类资产作为弹性配置，适合在市场机会中把握收益；商品类资产则以小众补充的方式进行分散，以降低单一资产类别带来的周期风险。</p>
    <p style="margin:0 0 8px 0;">从整体框架看，此配置强调“稳中求进、平衡分散”的思路，既重视资金的稳妥保值，也兼顾中长期收益的增长潜力。投资者在实际执行过程中，应结合自身体量、风险承受能力和市场变化，审慎评估配置比例的适配性与调整时机。</p>
    <p style="margin:8px 0 0 0;"><strong>风险提示：</strong>本配置仅为策略分享，不构成投资建议，您应结合自身风险承受能力决策。</p>
  </div>

  <div style="color:#666;font-size:12px;margin-top:20px;">如果不再接收推送，请<a href="${unsubscribeUrl}">点击取消订阅</a></div>
</body>
</html>
