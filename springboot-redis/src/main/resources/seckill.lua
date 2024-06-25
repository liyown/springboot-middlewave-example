local voucherId = ARGV[1]
local userId = ARGV[2]
local orderId = ARGV[3]
local voucherIdStockKey = 'stock:' .. voucherId
local goodsStock = tonumber(redis.call('get', voucherIdStockKey))
if goodsStock <= 0 then
    return 0
else
    redis.call('decr', voucherIdStockKey)
    redis.call('xadd', 'stream.order', '*', 'id', orderId, 'userId', userId, 'voucherId', voucherId)
    return 1
end
