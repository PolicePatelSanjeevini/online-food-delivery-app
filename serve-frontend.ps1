$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$python = (Get-Command python -ErrorAction Stop).Source
& $python -m http.server 5500 --directory (Join-Path $root 'frontend')
